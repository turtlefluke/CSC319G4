private class DiffImage extends JPanel implements ActionListener, ChangeListener {

        private static final long serialVersionUID = 0;
        
        private BufferedImage image1;
        private BufferedImage image2;
        private String image1name;
        private String image2name;
        private int numDiffPixels;
        private int opacity = 50;
        private String label1Text = "Expected";
        private String label2Text = "Actual";
        private boolean highlightDiffs = false;
        
        private Color highlightColor = new Color(224, 0, 224);
        private JLabel image1Label;
        private JLabel image2Label;
        private JLabel diffPixelsLabel;
        private JSlider slider;
        private JCheckBox box;
        private JMenuItem saveAsItem;
        private JMenuItem setImage1Item;
        private JMenuItem setImage2Item;
        private JFrame frame;
        private JButton colorButton;
        
        public DiffImage() {}
        
        public DiffImage(String file1, String file2) throws IOException {
            setImage1(file1);
            setImage2(file2);
            display();
        }
