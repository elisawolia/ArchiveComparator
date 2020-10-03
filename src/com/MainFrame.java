package com;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static com.ArchiveComparator.executeComparing;

/**
 * A JFrame which asks for the paths to the archives. The okButton executes
 * the comparing with the {@link ArchiveComparator} static method.
 *
 * @author elizavetavolianica
 */

public class MainFrame extends JFrame {
	private JLabel			archiveOld;
	private JLabel			archiveNew;
	private JButton			choseOld;
	private JButton 		choseNew;
	private JButton			cancelButton;
	private JButton			okButton;
	private JFileChooser	fileChooser;

	private File 			fileOld;
	private File 			fileNew;

	public MainFrame() {
		super("Archive Comparator");
		setLayout(new GridBagLayout());

		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Archives", "zip", "jar"));

		archiveOld = new JLabel("Old Archive:");
		archiveNew = new JLabel("New Archive:");
		choseOld = new JButton("Set Old");
		choseNew = new JButton("Set New");
		cancelButton = new JButton("Cancel");
		okButton = new JButton("Ok");

		setButtons();

		setSize(380, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void setButtons() {
		choseNew.setPreferredSize(new Dimension(100, 18));
		choseOld.setPreferredSize(new Dimension(100, 18));
		okButton.setPreferredSize(new Dimension(130, 20));
		cancelButton.setPreferredSize(new Dimension(130, 20));

		setGrid();
		setListeners();
	}

	private void setGrid() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weighty = 0.3;
		gc.weightx = 1;
		gc.gridx = 0;
		gc.gridy = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,15);
		add(archiveOld, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0,5,0,0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(choseOld, gc);

		gc.gridy = 1;
		gc.insets = new Insets(0,5,0,0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(choseNew, gc);

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,15);
		add(archiveNew, gc);
		gc.gridy = 2;
		gc.insets = new Insets(0,0,0,5);
		add(cancelButton, gc);

		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		add(okButton, gc);
	}

	private void setListeners() {
		choseOld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					fileOld = fileChooser.getSelectedFile();
				}
			}
		});

		choseNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					fileNew = fileChooser.getSelectedFile();
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileOld == null || fileNew == null)
				{
					JOptionPane.showMessageDialog(MainFrame.this, "Please attach the archives!");
					return ;
				}
				String pathOld = fileOld.getAbsolutePath();
				String pathNew = fileNew.getAbsolutePath();
				try {
					executeComparing(pathOld, pathNew);
					JOptionPane.showMessageDialog(MainFrame.this, "Saved to the results.txt");
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage());
				}
				System.exit(0);
			}
		});
	}
}
