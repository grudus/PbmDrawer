package com.grudus.pbmdrawer.components.reusable;


import com.grudus.pbmdrawer.PbmDrawerProperties;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputField extends JTextField implements FocusListener {

    private String hint;
    private InputType inputType;

    private boolean error;
    private int maxLength;

    private Color hintColor = PbmDrawerProperties.DEFAULT_HINT_COLOR;
    private Color textColor = PbmDrawerProperties.DEFAULT_TEXT_COLOR;
    private Color errorBorderColor = PbmDrawerProperties.DEFAULT_ERROR_BORDER_COLOR;

    private Border normalBorder;

    public enum InputType {
        NUMBERS,
        TEXT
    }

    public InputField(String hint, InputType inputType) {
        this.hint = hint;
        this.inputType = inputType;
        addFocusListener(this);

        normalBorder = getBorder();

        setForeground(hintColor);
        setText(hint);

        Font font = getFont();

        setFont(new Font(font.getFontName(), Font.PLAIN, 16));


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                final char key = keyEvent.getKeyChar();

                if (getText().length() == maxLength) {
                    error = true;
                    showError(true);
                    keyEvent.consume();
                    return;
                }

                if (InputField.this.inputType == InputType.NUMBERS && !Character.isDigit(key)) {
                    showError(true);
                    keyEvent.consume();
                    error = true;
                    return;
                }
                error = false;
                super.keyTyped(keyEvent);
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                if (error) showError(false);
            }
        });
    }

    public void setMaxLength(int length) {
        if (length < 0)
            throw new IllegalArgumentException("Length cannot be less than 0!");
        this.maxLength = length;
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        if (getText().equals(hint)) {
            setForeground(textColor);
            setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        if (getText().isEmpty()) {
            setForeground(hintColor);
            setText(hint);
        }
    }

    public boolean isEmpty() {
        return getText().equals(hint) || getText().isEmpty();
    }

    public void showError(boolean error) {
        InputField.this.setBorder(error ? new LineBorder(errorBorderColor, 1) : normalBorder);
    }
}
