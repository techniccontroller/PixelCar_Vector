package com.techniccontroller.print;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblPrintText;
	private JPanel panel;
	private final JTextArea txtAreaText;
	private JButton btnStart;
	private JButton btnExit;

	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPrintText = new JLabel("Text to write:");
		lblPrintText.setBounds(10, 11, 128, 14);
		contentPane.add(lblPrintText);
		
		panel = new JPanel();
		panel.setBounds(124, 0, 138, 301);
		contentPane.add(panel);
		
		DocumentFilter filter = new UppercaseDocumentFilter();
		
		txtAreaText = new JTextArea();
		txtAreaText.setFont(new Font("Monospaced", Font.PLAIN, 20));
		txtAreaText.setRows(10);
		txtAreaText.setColumns(7);
		txtAreaText.setLineWrap(true);
        txtAreaText.setWrapStyleWord(true);
        ((AbstractDocument) txtAreaText.getDocument()).setDocumentFilter(filter);
		panel.add(txtAreaText);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.write2(txtAreaText.getText());
			}
		});
		btnStart.setBounds(10, 307, 89, 23);
		contentPane.add(btnStart);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
				
			}
		});
		btnExit.setBounds(335, 307, 89, 23);
		contentPane.add(btnExit);
		
	}
}


// Class, so that the text field contains only uppercase letters 
class UppercaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, AttributeSet attr) throws BadLocationException {

        fb.insertString(offset, text.toUpperCase(), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
            String text, AttributeSet attrs) throws BadLocationException {

        fb.replace(offset, length, text.toUpperCase(), attrs);
    }
}
