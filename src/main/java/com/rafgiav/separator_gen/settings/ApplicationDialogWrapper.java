package com.rafgiav.separator_gen.settings;

import com.intellij.openapi.ui.DialogWrapper;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

public class ApplicationDialogWrapper extends DialogWrapper {

    private final String content;

    public ApplicationDialogWrapper(String content) {
        super(true); // use current window as parent
        this.content = content;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(content);
        label.setPreferredSize(new Dimension(100, 100));
        dialogPanel.add(label, BorderLayout.CENTER);

        return dialogPanel;
    }
}