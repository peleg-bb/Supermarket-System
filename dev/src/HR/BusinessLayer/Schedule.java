package HR.BusinessLayer;

import HR.DataAccessLayer.ShiftDAO;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedule {
    private final String store;
    private final Map<ShiftPair, Shift> shifts;
    private final ShiftDAO shiftDAO;


    public Schedule(String store, Date first_day, Time morn_start, Time morn_end, Time eve_start, Time eve_end, ShiftDAO shiftDAO) {
        this.store = store;
        shifts = new HashMap<>();
        this.shiftDAO = shiftDAO;
        initialize_week_shifts(first_day, morn_start, morn_end, eve_start, eve_end);
    }

    public Schedule(String store, Map<ShiftPair, Shift> shifts, ShiftDAO shiftDAO) {
        this.store = store;
        this.shifts = shifts;
        this.shiftDAO = shiftDAO;
    }
    private void initialize_week_shifts(Date first_day, Time morn_start, Time morn_end, Time eve_start, Time eve_end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first_day);
        for (int i = 0; i < 7; i++) {
            shifts.put(new ShiftPair(first_day, ShiftType.MORNING), new Shift(store, morn_start, morn_end));
            shifts.put(new ShiftPair(first_day, ShiftType.EVENING), new Shift(store,  eve_start, eve_end));
            shiftDAO.create_shift(first_day, ShiftType.MORNING, morn_start, morn_end, store);
            shiftDAO.create_shift(first_day, ShiftType.EVENING, eve_start, eve_end, store);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            first_day = calendar.getTime();
        }
        for (ShiftPair pair : shifts.keySet()) {
            scheduled_confirmation_check(new Date(pair.getDate().getTime() - 24 * 60 * 60 * 1000L), pair,  shifts.get(pair)); // subtract 24 hours from the date
        }
    }

    private void scheduled_confirmation_check(Date checkTime, ShiftPair pair, Shift shift) {
        // Set the time when the task should run (24 hours before the checkTime)
        long delay = checkTime.getTime() - new Date().getTime();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            try {
                send_mail(pair, shift);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }, delay, TimeUnit.SECONDS);
    }

    private void send_mail(ShiftPair pair, Shift shift) throws MessagingException {
        if (!shift.is_confirmed()) {
            // Set the email properties
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            // Set the email account credentials
            String senderEmail = "206494015.322527375.hrmanager@gmail.com";
            //String password = "bengurion111";

            // Create a mail session with the email account credentials
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, "mwjrfkjonbnyfzmo");
                }
            });

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Format the date as a string in the desired format
            String myDate = dateFormat.format(pair.getDate());

            // Create a new email message
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse("206494015.322527375.hrmanager@gmail.com"));
            emailMessage.setSubject("Shift not confirmed alert");
            emailMessage.setText("ALERT: " + myDate + ", " + pair.getType().toString() + " shift in " + shift.get_store() + " is 24 hours from now and still isn't confirmed!");
            // Send the email message
            Transport.send(emailMessage);
        }
    }

    public String add_availability(Integer id, Date date_object, ShiftType type) {
        ShiftPair shiftPair = get_shift(date_object, type);
        if (shiftPair != null) {
            String res = shiftDAO.add_availability(id, date_object, type, store);
            if (res.equals("")) {
                return shifts.get(shiftPair).add_availability(id);
            }
            return res;
        }
        return "Shift doesn't exists";
    }

    private ShiftPair get_shift(Date date_object, ShiftType type) {
        for (ShiftPair shift_pair: shifts.keySet()) {
            if (shift_pair.equals(date_object, type)) {
                return shift_pair;
            }
        }
        return null;
    }

    public String remove_availability(Integer id, Date date_object, ShiftType type) {
        ShiftPair shiftPair = get_shift(date_object, type);
        if (shiftPair != null) {
            String res = shiftDAO.remove_availability(id, date_object, type, store);
            if (res.equals("")) {
                return shifts.get(shiftPair).remove_availability(id);
            }
            return res;
        }
        return "Shift doesn't exists";
    }

    public String get_availability(Integer id) {
        StringBuilder availability = new StringBuilder();
        for (ShiftPair pair: shifts.keySet()) {
            if (shifts.get(pair).is_available(id)) {
                availability.append(pair.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString()).append(" - ").append(pair.getType().toString()).append("\n");
            }
        }
        return availability.toString();
    }

    public String get_shifts(Integer id) {
        StringBuilder shifts_list = new StringBuilder();
        for (ShiftPair pair: shifts.keySet()) {
            String job = shifts.get(pair).is_assigned(id);
            if (!job.equals("")) {
                shifts_list.append(pair.getDate().toString()).append(" - ").append(pair.getType().toString()).append(", as ").append(job).append("\n");
            }
        }
        return shifts_list.toString();
    }

    public String confirm_shift(Date date_object, ShiftType shift) {
        String res = shiftDAO.confirm_shift(date_object, shift, store);
        if (res.equals("")) {
            return shifts.get(get_shift(date_object, shift)).confirm_shift();
        }
        return res;
    }

    public String assign_shift(int id_num, Date date_object, ShiftType shift_type, JobType role) {
        String res = shifts.get(get_shift(date_object, shift_type)).assign_shift(id_num, role);
        if (res.equals("")) {
            return shiftDAO.assign_shift(id_num, date_object, shift_type, role, store);
        }
        return res;
    }

    public String unassign_shift(int id_num, Date date_object, ShiftType shift_type, JobType job) {
        String res = shiftDAO.unassign_shift(id_num, date_object, shift_type, job, store);
        if (res.equals("")) {
            return shifts.get(get_shift(date_object, shift_type)).unassign_shift(id_num, job);
        }
        return res;
    }

    public String limit_work(int id_num, Date date_object, ShiftType shift_type) {
        String res = shiftDAO.limit_work(id_num, date_object, shift_type, store);
        if (res.equals("")) {
            return shifts.get(get_shift(date_object, shift_type)).limit_work(id_num);
        }
        return res;
    }

    public String remove_worker_limit(int id_num, Date date_object, ShiftType shift_type) {
        String res = shiftDAO.remove_worker_limit(id_num, date_object, shift_type, store);
        if (res.equals("")) {
            return shifts.get(get_shift(date_object, shift_type)).remove_worker_limit(id_num);
        }
        return res;
    }

    public List<Integer> show_shift_availability(Date date_object, ShiftType shift_type) {
        return shifts.get(get_shift(date_object, shift_type)).show_shift_availability();
    }

    public int shifts_limit(int id, Date date_object) {
        int counter = 0;
        for (ShiftPair pair: shifts.keySet()) {
            if (!shifts.get(pair).is_assigned(id).equals("")) {
                if (pair.getDate() == date_object) {
                    return -1;
                }
                counter = counter + 1;
            }
        }
        return counter;
    }

    public boolean has_future_shifts(Date date, int id_num) {
        for (ShiftPair pair: shifts.keySet()) {
            if (pair.getDate().after(date)) {
                if (!shifts.get(pair).is_assigned(id_num).equals("")) {
                    return true;
                }
            }
        }
        return false;
    }

    public double get_hours(Date date_object, ShiftType shift_type) {
        return shifts.get(get_shift(date_object, shift_type)).get_length();
    }

    public boolean check_availability(Timestamp arrivalTime) {
        Date date = new Date(arrivalTime.getNanos());
        for (ShiftPair pair: shifts.keySet()) {
            if (pair.getDate() == date && (shifts.get(pair).get_start().before(date) && shifts.get(pair).get_end().after(date))) {
                return shifts.get(pair).check_availability();
            }
        }
        return false;
    }

    public List<String> get_available_drivers(Timestamp startTime, Timestamp endTime) {
        Date start = new Date(startTime.getNanos());
        Date end = new Date(endTime.getNanos());
        for (ShiftPair pair: shifts.keySet()) {
            if (pair.getDate() == start && pair.getDate() == end && (shifts.get(pair).get_start().before(start) && shifts.get(pair).get_end().after(start)) && (shifts.get(pair).get_start().before(end) && shifts.get(pair).get_end().after(end))) {
                return shifts.get(pair).get_available_drivers();
            }
        }
        return null;
    }

    public boolean assign_drivers(int id, Timestamp startTime, Timestamp endTime) {
        Date start = new Date(startTime.getNanos());
        Date end = new Date(endTime.getNanos());
        for (ShiftPair pair: shifts.keySet()) {
            if (pair.getDate() == start && pair.getDate() == end && (shifts.get(pair).get_start().before(start) && shifts.get(pair).get_end().after(start)) && (shifts.get(pair).get_start().before(end) && shifts.get(pair).get_end().after(end))) {
                String res = shifts.get(pair).assign_shift(id, JobType.DRIVER);
                return res.equals("");
            }
        }
        return false;
    }

    public boolean has_future_shifts_role(Date date, JobType role, int id_num) {
        for (ShiftPair pair: shifts.keySet()) {
            if (pair.getDate().after(date)) {
                if (shifts.get(pair).is_assigned_to_role(id_num, role)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean current_week(int day) {
        Calendar calendar = Calendar.getInstance();
        Map.Entry<ShiftPair, Shift> firstEntry = shifts.entrySet().iterator().next();
        ShiftPair key = firstEntry.getKey();
        calendar.setTime(key.getDate());
        int week_day = calendar.get(Calendar.WEEK_OF_YEAR);
        return week_day >= day;
    }

    public String cancel_product(int id, int product_id_num, Date date_object, ShiftType type) {
        String res = shiftDAO.cancel_product(id, product_id_num, date_object, type, store);
        if (res.equals("")) {
            return shifts.get(get_shift(date_object, type)).cancel_product(id, product_id_num);
        }
        return res;
    }

    public Map<JobType, List<Integer>> show_shift_assigned(Date date_object, ShiftType shift_type) {
        return shifts.get(get_shift(date_object, shift_type)).show_shift_assigned();
    }

    public boolean is_limited(int id, Date date_object, ShiftType shift_type) {
        return shifts.get(get_shift(date_object, shift_type)).is_limited(id);
    }
}
