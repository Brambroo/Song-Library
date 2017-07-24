import static org.junit.Assert.*;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.junit.Test;

import edu.cnu.cs.gooey.Gooey;
import edu.cnu.cs.gooey.GooeyDialog;
import edu.cnu.cs.gooey.GooeyFrame;

/**
 * Test class for SongLibrary
 * 
 * @author Christian Clarke
 * @version 3/3/17
 */
public class SongLibraryTest {

    /**
     * Tests if the add button is clickable and delete is disabled
     */
    @Test
    public void doesTheAddButtonClickw() {
        Gooey.capture(new GooeyFrame() {

            @Override
            public void invoke() {
                SongLibrary.main(new String[] {});
            }

            @Override
            public void test(JFrame frame) {
                assertTrue("Frame should be visable", frame.isVisible());

                // DefaultTableModel tablemodel = Gooey.getComponent(frame,
                // JSc);

                Box frameBox = Gooey.getComponent(frame, Box.class);
                JButton add = Gooey.getButton(frameBox, "Add");
                JButton delete = Gooey.getButton(frameBox, "Delete");
                assertFalse(delete.isEnabled());

                add.doClick();
            }

        });
    }

    /**
     * Tests if the About Menu Item displays the correct information
     */
    @Test
    public void doesAboutDisplayInformation() {
        Gooey.capture(new GooeyFrame() {

            @Override
            public void invoke() {
                SongLibrary.main(new String[] {});
            }

            @Override
            public void test(JFrame frame) {
                assertTrue("Frame should be visable", frame.isVisible());

                JMenuBar bar = Gooey.getMenuBar(frame);
                JMenu songLibrary = Gooey.getMenu(bar, "SongLibrary");
                JMenuItem about = Gooey.getMenu(songLibrary, "About...");

                Gooey.capture(new GooeyDialog() {

                    @Override
                    public void invoke() {
                        about.doClick();
                    }

                    @Override
                    public void test(JDialog dialog) {
                        assertEquals("Title is not correct", "About...",
                                dialog.getTitle());
                        Gooey.getLabel(dialog,
                                "<html><hr><i>Song Library</i><br>by Christian Clarke<hr></html>");
                        JButton ok = Gooey.getButton(dialog, "OK");
                        ok.doClick();

                    }

                });
            }

        });
    }

}
