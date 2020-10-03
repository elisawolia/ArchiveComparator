package com;

import javax.swing.*;
import java.io.IOException;
import static com.ArchiveComparator.executeComparing;

/**
 * Entry point for the program. It attempts to get the args,
 * otherwise creates a new JFrame {@link MainFrame}.
 *
 * @author elizavetavolianica
 **/

public class ArchCompMain {
    public static void main(String[] args) {
    	if (args.length == 2)
		{
			try {
				executeComparing(args[0], args[1]);
				System.out.println("Saved to the results.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    else
	    {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new MainFrame();
				}
			});
		}
    }
}
