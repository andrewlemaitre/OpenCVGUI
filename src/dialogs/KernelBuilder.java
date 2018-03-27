package dialogs;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JFormattedTextField;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.util.ArrayList;
import java.awt.Component;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.w3c.dom.events.MouseEvent;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Insets;

public class KernelBuilder {

    JDialog kernelDialog;
    Box kernelBox;
    ArrayList<KernelRow> rowList = new ArrayList<>();
    Mat kernelData;
    JFormattedTextField resizeXTextField;
    JFormattedTextField resizeYTextField;
    JFormattedTextField scaleNumeratorTextField;
    JFormattedTextField scaleDenominatorTextField;
    
    public KernelBuilder( Mat kernelData ) {
        this.kernelData = kernelData;
        initialize();
        createRows( kernelData );
    }
    
    void initialize() {
        kernelDialog = new JDialog();
        kernelDialog.setSize(800, 600);
        kernelDialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        
        JPanel topPane = new JPanel();
        topPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        kernelDialog.getContentPane().add(topPane, BorderLayout.NORTH);
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
        
        Component tpGlue1 = Box.createHorizontalGlue();
        topPane.add(tpGlue1);
        
        Box tpBox = Box.createHorizontalBox();
        tpBox.setPreferredSize(new Dimension(120, 25));
        tpBox.setMinimumSize(new Dimension(150, 25));
        tpBox.setMaximumSize(new Dimension(150, 25));
        topPane.add(tpBox);
        
        resizeXTextField = new JFormattedTextField();
        resizeXTextField.setMaximumSize(new Dimension(2147483647, 21));
        resizeXTextField.setHorizontalAlignment(SwingConstants.CENTER);
        resizeXTextField.setValue((int)kernelData.width());
        resizeXTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if( (int)resizeXTextField.getValue() <= 0 )
                    resizeXTextField.setValue( 1 );
            }
        });
        tpBox.add(resizeXTextField);
        
        JLabel resizeSeparatorLabel = new JLabel("X");
        tpBox.add(resizeSeparatorLabel);
        
        resizeYTextField = new JFormattedTextField();
        resizeYTextField.setMaximumSize(new Dimension(2147483647, 21));
        resizeYTextField.setHorizontalAlignment(SwingConstants.CENTER);
        resizeYTextField.setValue((int)kernelData.height());
        resizeYTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if( (int)resizeYTextField.getValue() <= 0 )
                    resizeYTextField.setValue( 1 );
            }
        });
        tpBox.add(resizeYTextField);
        
        JButton resizeButton = new JButton();
        resizeButton.setAction( new ResizeAction() );
        tpBox.add(resizeButton);
        
        Component tpGlue2 = Box.createHorizontalGlue();
        topPane.add(tpGlue2);
        
        JPanel leftPane = new JPanel();
        leftPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        kernelDialog.getContentPane().add(leftPane, BorderLayout.WEST);
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.LINE_AXIS));
        
        Component lpStrut1 = Box.createHorizontalStrut(20);
        lpStrut1.setPreferredSize(new Dimension(5, 0));
        lpStrut1.setMinimumSize(new Dimension(5, 0));
        leftPane.add(lpStrut1);
        
        Box lpBox = Box.createVerticalBox();
        leftPane.add(lpBox);
        
        Component verticalGlue = Box.createVerticalGlue();
        lpBox.add(verticalGlue);
        
        scaleNumeratorTextField = new JFormattedTextField();
        scaleNumeratorTextField.setHorizontalAlignment(SwingConstants.CENTER);
        scaleNumeratorTextField.setMaximumSize(new Dimension(40, 20));
        scaleNumeratorTextField.setPreferredSize(new Dimension(40, 20));
        scaleNumeratorTextField.setValue(1.0f);
        lpBox.add(scaleNumeratorTextField);
        
        Component verticalStrut = Box.createVerticalStrut(20);
        verticalStrut.setMaximumSize(new Dimension(0, 20));
        verticalStrut.setPreferredSize(new Dimension(0, 5));
        verticalStrut.setMinimumSize(new Dimension(0, 5));
        lpBox.add(verticalStrut);
        
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(0, 1));
        separator.setMaximumSize(new Dimension(40, 1));
        lpBox.add(separator);
        
        Component verticalStrut_1 = Box.createVerticalStrut(20);
        verticalStrut_1.setMaximumSize(new Dimension(0, 20));
        verticalStrut_1.setMinimumSize(new Dimension(0, 5));
        verticalStrut_1.setPreferredSize(new Dimension(0, 5));
        lpBox.add(verticalStrut_1);
        
        scaleDenominatorTextField = new JFormattedTextField();
        scaleDenominatorTextField.setMinimumSize(new Dimension(40, 20));
        scaleDenominatorTextField.setPreferredSize(new Dimension(40, 20));
        scaleDenominatorTextField.setHorizontalAlignment(SwingConstants.CENTER);
        scaleDenominatorTextField.setMaximumSize(new Dimension(40, 20));
        scaleDenominatorTextField.setValue(1.0f);
        lpBox.add(scaleDenominatorTextField);
        
        Component verticalStrut_2 = Box.createVerticalStrut(20);
        verticalStrut_2.setMaximumSize(new Dimension(32767, 5));
        verticalStrut_2.setMinimumSize(new Dimension(0, 5));
        verticalStrut_2.setPreferredSize(new Dimension(0, 5));
        lpBox.add(verticalStrut_2);
        
        JButton scaleButton = new JButton("Scale");
        scaleButton.setVerticalTextPosition(SwingConstants.TOP);
        scaleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        scaleButton.setPreferredSize(new Dimension(50, 23));
        scaleButton.setMinimumSize(new Dimension(50, 23));
        scaleButton.setMaximumSize(new Dimension(50, 23));
        scaleButton.setMargin(new Insets(2, 2, 2, 2));
        scaleButton.setAction( new ScaleAction() );
        lpBox.add(scaleButton);
        
        Component verticalGlue_1 = Box.createVerticalGlue();
        lpBox.add(verticalGlue_1);
        
        Component lpStrut2 = Box.createHorizontalStrut(20);
        lpStrut2.setPreferredSize(new Dimension(5, 0));
        lpStrut2.setMinimumSize(new Dimension(5, 0));
        leftPane.add(lpStrut2);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        kernelDialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        kernelBox = Box.createVerticalBox();
        scrollPane.setViewportView(kernelBox);
    }
    
    public JDialog getDialog() {
        return kernelDialog;
    }

    private void resize() {
        Mat clone = kernelData.clone();
        Imgproc.resize(kernelData, kernelData, new Size( (int)resizeXTextField.getValue(), (int)resizeYTextField.getValue() ) );
        
        for( int row = 0; row < kernelData.height(); row++ ) {
            for( int col = 0; col < kernelData.width(); col ++ ) {
                if( col < clone.width() && row < clone.height() ) {
                    double[] data = clone.get(row, col);
                    kernelData.put(row, col, data);
                } else {
                    kernelData.put(row, col, new float[] {0f} );
                }
            }
        }
        clone.release();
        refreshDialog();
    }
    
    private void refreshDialog() {
        clearRows();
        createRows( kernelData );
        kernelDialog.revalidate();
        kernelDialog.repaint();
        kernelDialog.pack();
    }
    
    private void clearRows() {
        rowList.clear();
        kernelBox.removeAll();
    }

    private void createRows( Mat kernelData ) {
        for( int i = 0; i < kernelData.rows(); i++ ) {
            Mat rowData = kernelData.row(i);
            KernelRow kr = new KernelRow( rowData );
            kernelBox.add( kr.getRowPanel() );
            rowList.add(kr);
        }
    }
    
    private class KernelRow {
        
        Mat data;
        JPanel kernelRowPanel = new JPanel();
        
        public KernelRow( Mat data ) {
            this.data = data;
            createRow();
        }
        
        private void createRow() {
            FlowLayout fl_kernelRowPanel = (FlowLayout) kernelRowPanel.getLayout();
            fl_kernelRowPanel.setVgap(0);
            kernelRowPanel.setMaximumSize(new Dimension(32767, 25));
            for( int i = 0; i < data.cols(); i++ ) {
                //Add a new text field
                JFormattedTextField formattedTextField = new JFormattedTextField();
                formattedTextField.setMaximumSize(new Dimension(50, 20));
                formattedTextField.setPreferredSize(new Dimension(50, 20));
                formattedTextField.setMinimumSize(new Dimension(50, 20));
                formattedTextField.setValue(data.get(0, i)[0]);
                formattedTextField.setHorizontalAlignment( SwingConstants.CENTER );
                
                //Add a property change listener to the formatted text field.
                formattedTextField.addPropertyChangeListener("value", new TextFieldPropertyListener( i, data ) );
                
                //Add this formatted text field to the row panel.
                kernelRowPanel.add(formattedTextField);
                //Add a new separator unless it's the last element in the list.
                if( i < data.cols()-1 ) {
                    JSeparator separator = new JSeparator();
                    separator.setMaximumSize(new Dimension(1, 25));
                    separator.setPreferredSize(new Dimension(1, 25));
                    separator.setMinimumSize(new Dimension(1, 25));
                    separator.setOrientation(SwingConstants.VERTICAL);
                    kernelRowPanel.add(separator);
                }
            }
        }
        
        public JPanel getRowPanel() {
            return kernelRowPanel;
        }
    }
    
    private class ScaleAction extends AbstractAction {
        public ScaleAction() {
            super();
            putValue("Name", "Scale");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Core.multiply(kernelData, new Scalar((float)scaleNumeratorTextField.getValue()/(float)scaleDenominatorTextField.getValue()), kernelData);
            refreshDialog();
        }
    }
    
    private class ResizeAction extends AbstractAction {
        public ResizeAction() {
            super();
            putValue("Name", "Resize");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            resize();
        }
    }
    
    private class TextFieldPropertyListener implements PropertyChangeListener  {
        
        int location;
        Mat data;
        
        public TextFieldPropertyListener( int location, Mat data ) {
            this.location = location;
            this.data = data;
        }
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            data.put(0, location, (double)evt.getNewValue());
        }
    }

}
