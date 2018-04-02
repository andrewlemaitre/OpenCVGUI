package dialogs;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import javax.swing.JList;
import javax.swing.JButton;
import miscellaneous.Helper;
import openCVHarness.OpenCVHarnessWindow;
import operations.OpenCVOperation;

import java.awt.Dimension;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Action;

//TODO: When a source mat selector is created, make it appear near the mouse or another location.
//TODO: Add a listcellrenderer like in WebcamHarnessWindow to show the output name of the operation in the list instead of the operation name.
public class SrcMatSelectorDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    OpenCVOperation parentOperation;
    DefaultListModel<OpenCVOperation> availableSourceMatsList;
    JList<OpenCVOperation> list;
    private final Action applySourceAction = new ApplySourceAction();
    private final Action closeAction = new CloseAction();

    public SrcMatSelectorDialog(OpenCVOperation openCVOperation) {
        super();
        setSize(new Dimension(300, 300));
        setType(Type.UTILITY);
        setModalityType(DEFAULT_MODALITY_TYPE);
        setTitle("Source Mat Selector");
        parentOperation = openCVOperation;

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);

        availableSourceMatsList = new DefaultListModel<>();
        list = new JList<>(availableSourceMatsList);
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(list);

        JPanel panel_1 = new JPanel();
        panel.add(panel_1);
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

        JButton btnNewButton = new JButton("Select Source");
        btnNewButton.setAction(applySourceAction);
        panel_1.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Close");
        btnNewButton_1.setAction(closeAction);
        panel_1.add(btnNewButton_1);

        populatePanel();
        this.setVisible(true);
    }

    void populatePanel() {
        OpenCVHarnessWindow whw = Helper.getWebcamHarnessWindow();
        ArrayList<OpenCVOperation> operationsList = whw.getListManager().getOperationsArrayList();
//        DefaultListModel<OpenCVOperation> imageOpList = whw.getListManager().getOperationsList();
        int foundIndex = 0;
        for(int i = 0; i < operationsList.size(); i++) {
            if(operationsList.get(i).equals(parentOperation)) {
                foundIndex = i;
                break;
            }
            if(i == operationsList.size()-1) {
                System.err.println("Parent operation was not found in the operations list.");
                return;
            }
        }
        if(foundIndex == 0) {
            System.err.println("Parent operation appears to be first operation in the list.. so there will be no source mats available.");
            return;
        }

        for(int i = 0; i < foundIndex; i++) {
            availableSourceMatsList.addElement(operationsList.get(i));
        }
    }

    private class ApplySourceAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        public ApplySourceAction() {
            putValue(NAME, "Select Source");
            putValue(SHORT_DESCRIPTION, "Some short description");
        }
        public void actionPerformed(ActionEvent e) {
            if(list.getSelectedIndex() != -1) {
                parentOperation.setInputOperation(list.getSelectedValue());
                dispose();
            }
        }
    }

    private class CloseAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        public CloseAction() {
            putValue(NAME, "Close");
            putValue(SHORT_DESCRIPTION, "Some short description");
        }
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
