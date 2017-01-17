package com.gans.csv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MergeCsv {

    private static Map<String, File> files = new HashMap<>();
    private static SystemSettings sSettings;
    private static UserSettings uSettings;

    public static void main(String[] args) throws Exception {
        JFrame frame = frame();

        JPanel panel = new JPanel();
        frame.add(panel);

        sSettings = new SystemSettings();
        uSettings = new UserSettings();

        String leftCsvFile = "left csv file";
        String rightCsvFile = "right csv file";

        addButton(panel, leftCsvFile);
        addButton(panel, rightCsvFile);

        frame.setVisible(true);
    }

    private static JFrame frame() {
        JFrame frame = new JFrame();
        frame.setTitle("merge csv");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private static void addButton(JPanel panel, String btnLabel) {
        JButton btn = getButton(btnLabel, file -> files.put(btnLabel, file));
        panel.add(btn);
    }

    private static JButton getButton(String label, Consumer<File> fileConsumer) {
        JButton btn = new JButton(label);
        btn.setSize(100, 10);
        btn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                String dir = uSettings.getSetting(label);

                JFileChooser fc = new JFileChooser(dir);
                if (fc.showOpenDialog(btn.getParent()) == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();

                    dir = file.getParentFile().getAbsolutePath();
                    uSettings.setSetting(label, dir);

                    btn.setText(file.getName());

                    fileConsumer.accept(file);
                }
            }
        });
        return btn;
    }

}
