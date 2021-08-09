package com.rafgiav.separator_gen.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.JBIntSpinner;
import com.rafgiav.separator_gen.SeparatorGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ApplicationSettingsComponent {
    private JButton previewSeparator;
    private JPanel panel1;
    private ComboBox<String> innerLineStyle;
    private JBIntSpinner lineLength;
    private JTextField sampleTextField;

    public JPanel getPanel() {
        return panel1;
    }
    private static final String[] AVAILABLE_STYLES = {"-", "_", "─"};


    public JComponent getPreferredFocusedComponent() {
        return innerLineStyle;
    }

    public String getInnerLineStyle() {
        return innerLineStyle.getItem();
    }

    public void setInnerLineStyle(String innerLineStyle) {
        this.innerLineStyle.setSelectedItem(innerLineStyle);
    }

    public int getLineLength() {
        return lineLength.getNumber();
    }

    public void setLineLength(int lineLength) {
        this.lineLength.setNumber(lineLength);
    }

    public String getPreviewSeparator(){
        if(SeparatorGenerator.isGenerationPossible(sampleTextField.getText(), getLineLength(), " ")){
            return SeparatorGenerator.generateHorizontalSeparator(sampleTextField.getText(), getInnerLineStyle(), null, getLineLength());
        }
        return null;
    }

    private void createUIComponents() {
        lineLength = new JBIntSpinner(80,8,150,1);
        innerLineStyle = new ComboBox<>(AVAILABLE_STYLES);
        previewSeparator = new JButton();
        previewSeparator.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String preview = getPreviewSeparator();
                ApplicationDialogWrapper dialog = new ApplicationDialogWrapper(preview!=null?preview:"Could not generate preview!");
                dialog.setTitle("Separator Preview");
                dialog.show();
            }
        });
    }
}
