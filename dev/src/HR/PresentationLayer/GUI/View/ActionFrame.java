package HR.PresentationLayer.GUI.View;

import HR.PresentationLayer.GUI.Model.ActionModel;

import javax.swing.*;

public class ActionFrame extends AbstractFrame{
    private JTextField[] fieldsText;

    public ActionFrame(String title, int fieldsNum, String[] fields) {
        super(2, new ActionModel());
        fieldsText = new JTextField[fieldsNum];
        setTitle(title);
        setResizable(false);


        for (int i = 0; i < fieldsNum; i++) {
            JLabel Label = new JLabel(fields[i] + ":");
            JTextField field = new JTextField(20);
            fieldsText[i] = field;
            JPanel x = new JPanel();
            x.add(Label);
            x.add(field);
            add(x);
        }

        addButton("Done");
        addButton("Back");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String get_field(int index) {
        return fieldsText[index].getText();
    }

    public int get_num_fields() {
        return fieldsText.length;
    }

    public String get_title() {
        return getTitle();
    }
}
