package openCVHarness;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import javax.swing.Action;

import dialogs.NewOperationDialog;

import miscellaneous.Helper;

import operations.ImReadOperation;

@SuppressWarnings("serial")
public class OpenCVHarnessWindow extends JFrame {

    /** Default serial ID */
    private static final long serialVersionUID = 1L;
    OpenCVHarness webcamHarness;
    ImagePanelOrganizer imagePanelOrganizer;
    OperationsManager operationsManager = new OperationsManager();

    private final Action runOperationsAction = new RunOperationsAction();
    private final Action createNewOperationDialogAction = new CreateNewOperationDialogAction();
    private final Action removeOperationAction = new RemoveOperationAction();
    private final Action copyOperationAction = new CopyOperationAction();
    private final Action editOperationAction = new EditOperationAction();
    private final Action newImagePanelAction = new NewImagePanelAction();

    public OpenCVHarnessWindow(OpenCVHarness webcamHarness) {
        this.webcamHarness = webcamHarness;
        Helper.setWebcamHarnessWindow(this);
        initialize();
    }

    void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Frame");
        this.setSize(1024,768);
        this.setLocation(50, 50);
        this.setVisible(true);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel viewerPanel = new JPanel();
        getContentPane().add(viewerPanel, BorderLayout.CENTER);
        viewerPanel.setLayout(new BorderLayout(0, 0));

        imagePanelOrganizer = new ImagePanelOrganizer();
        viewerPanel.add(imagePanelOrganizer.getOrganizerPanel());

        imagePanelOrganizer.createNewImagePanel();
        JPanel viewerControlsPanel = new JPanel();
        viewerPanel.add(viewerControlsPanel, BorderLayout.SOUTH);
        viewerControlsPanel.setLayout(new BoxLayout(viewerControlsPanel, BoxLayout.X_AXIS));

        JButton runOperationsButton = new JButton();
        runOperationsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        runOperationsButton.setAction(runOperationsAction);
        viewerControlsPanel.add(runOperationsButton);

        JButton button = new JButton("New button");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAction(newImagePanelAction);
        viewerControlsPanel.add(button);

        JPanel operationsPanel = new JPanel();
        getContentPane().add(operationsPanel, BorderLayout.EAST);

        JPanel operationsListPanel = new JPanel();
        operationsPanel.add(operationsListPanel);
        operationsListPanel.setLayout( new BoxLayout(operationsListPanel, BoxLayout.PAGE_AXIS));

        JScrollPane operationsListScrollPane = new JScrollPane(operationsManager.getOperationsTree());
        operationsListScrollPane.setPreferredSize( new Dimension(368, 240));
        operationsListPanel.add(operationsListScrollPane);

        JPanel operationsListButtonPanel = new JPanel();
        operationsListButtonPanel.setLayout( new BoxLayout(operationsListButtonPanel, BoxLayout.LINE_AXIS));
        operationsListPanel.add(operationsListButtonPanel);

        JButton addOperationButton = new JButton();
        addOperationButton.setAction(createNewOperationDialogAction);
        operationsListButtonPanel.add(addOperationButton);
        JButton CopyOperationButton = new JButton();
        CopyOperationButton.setAction(copyOperationAction);
        operationsListButtonPanel.add(CopyOperationButton);
        JButton RemoveOperationButton = new JButton();
        RemoveOperationButton.setAction(removeOperationAction);
        operationsListButtonPanel.add(RemoveOperationButton);
        JButton editOperationButton = new JButton();
        editOperationButton.setAction(editOperationAction);
        operationsListButtonPanel.add(editOperationButton);

        JPanel menuBarPanel = new JPanel();
        getContentPane().add(menuBarPanel, BorderLayout.NORTH);
        menuBarPanel.setLayout(new BoxLayout(menuBarPanel, BoxLayout.X_AXIS));

        JMenuBar menuBar = new JMenuBar();
        menuBarPanel.add(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem saveOperationsMenuItem = fileMenu.add(new JMenuItem());
        saveOperationsMenuItem.setAction(new SaveOperationsAction());
        JMenuItem loadOperationsMenuItem = fileMenu.add(new JMenuItem());
        loadOperationsMenuItem.setAction(new LoadOperationsAction());

        addTestingOperations();

    }

    private void addTestingOperations() {
//        operationsManager.addOperation(new operations.CvtColorOperation());
//        operationsManager.addOperation(new operations.DistanceTransformOperation());
        ImReadOperation iro = (ImReadOperation) operationsManager.addOperation( new operations.ImReadOperation());
        iro.setOutputName("Image Read test");
        iro.getFile().setValue( new File("C:/Users/lemaitrea/Documents/Skittles_1.jpg"));
//        operationsManager.addOperation(new operations.ResizeOperation());
//        operationsManager.addOperation(new operations.ThresholdOperation());
//        operationsManager.addOperation(new operations.TranslationOperation());
//        operationsManager.addOperation(new operations.RotationOperation());
    }

    public OperationsManager getListManager() {
        return operationsManager;
    }

    void refreshMainView() {
        this.revalidate();
        this.repaint();
    }

    void createNewOperationDialog() {
        NewOperationDialog nod = new NewOperationDialog(this);
        nod.setLocationRelativeTo(this);
        nod.setVisible(true);
    }

    private class LoadOperationsAction extends AbstractAction {
        public LoadOperationsAction() {
            putValue(NAME,"Load Operations");
        }
        public void actionPerformed( ActionEvent e) {
            System.out.println("Clicked load operations.");

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Load image operations");

            int returnVal = chooser.showOpenDialog((JMenuItem)e.getSource());

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File loadFile = chooser.getSelectedFile();
                System.out.println("Save as file " + loadFile.getPath());
                OpenCVSerializer.deserializeOperations(loadFile.getPath());
            }
        }
    }

    private class SaveOperationsAction extends AbstractAction {
        public SaveOperationsAction() {
            putValue(NAME,"Save Operations");
        }
        public void actionPerformed( ActionEvent e) {
            System.out.println("Clicked save operations.");

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save image operations");

            int returnVal = chooser.showSaveDialog((JMenuItem)e.getSource());

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File saveFile = chooser.getSelectedFile();
                System.out.println("Save as file " + saveFile.getPath());
                OpenCVSerializer.serializeOperations( operationsManager.getOperationsArrayList(), saveFile.getPath());
            }
        }
    }

    private class RunOperationsAction extends AbstractAction {
        public RunOperationsAction() {
            putValue(NAME, "Run Operations");
            putValue(SHORT_DESCRIPTION, "Performs all of the operations in the operations list.");
        }
        public void actionPerformed(ActionEvent e) {
            operationsManager.runOperations();
            refreshMainView();
        }
    }

    private class CreateNewOperationDialogAction extends AbstractAction {
        public CreateNewOperationDialogAction() {
            putValue(NAME, "New Operation");
            putValue(SHORT_DESCRIPTION, "Opens a dialog to create new operations.");
        }
        public void actionPerformed(ActionEvent e) {
            createNewOperationDialog();
        }
    }

    private class EditOperationAction extends AbstractAction {
        public EditOperationAction() {
            putValue(NAME, "Edit");
            putValue(SHORT_DESCRIPTION, "Edit the settings of the selected operation.");
        }
        public void actionPerformed(ActionEvent e) {
            operationsManager.editSelectedOperation();
        }
    }

    private class RemoveOperationAction extends AbstractAction {
        public RemoveOperationAction() {
            putValue(NAME, "Remove");
            putValue(SHORT_DESCRIPTION, "Remove the topmost selected operation.");
        }
        public void actionPerformed(ActionEvent e) {
            operationsManager.removeSelectedOperation();
        }
    }

    private class CopyOperationAction extends AbstractAction {
        public CopyOperationAction() {
            putValue(NAME, "Copy");
            putValue(SHORT_DESCRIPTION, "This button will create a new version of the currently selected operation.");
        }
        public void actionPerformed(ActionEvent e) {
            operationsManager.copySelectedOperation();
        }
    }

    private class NewImagePanelAction extends AbstractAction {
        public NewImagePanelAction() {
            putValue( NAME, "New Image Panel");
            putValue( SHORT_DESCRIPTION, "This button will create a new image viewer for viewing the output of an operation.");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            imagePanelOrganizer.createNewImagePanel();
        }
    }
}
