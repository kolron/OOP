package gameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginScreen extends JFrame implements Runnable, ActionListener
{
    private Dimension d;
    private JTextArea idArea;
    private JTextArea levelArea;
    private Ex2.Credentials cred;
    private JLabel idLabel;
    private JLabel levelLabel;
    private JButton enterButton;
    private JMenuBar menuBar;
    private JMenu menu;
    private Condition flag;
    private Lock lock;
    private JLabel messages;

    public LoginScreen(Ex2.Credentials cred)
    {
        super();
        this.cred = cred;
        setLayout(null);
        d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = d.width - 20;
        int y = d.height - 20;
        setBounds(0,0,d.width/2,d.height/2);
        setBackground(Color.WHITE);
        levelLabel = new JLabel("Level:");
        levelLabel.setBounds(40,30,120,40);
        add(levelLabel);

        levelArea = new JTextArea();
        levelArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        levelArea.setBounds(80,40,120,23);
        add(levelArea);

        idLabel = new JLabel("ID:");
        idLabel.setBounds(40,60,120,40);;
        add(idLabel);

        idArea = new JTextArea();
        idArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        idArea.setBounds(80,70,120,23);
        add(idArea);

        messages = new JLabel();
        messages.setBounds(80,150,400,23);
        messages.setForeground(Color.RED);
        add(messages);


        enterButton = new JButton("Enter");
        enterButton.setBounds(80,100,120,23);
        enterButton.addActionListener(this);
        add(enterButton);
        this.lock = new ReentrantLock();
        this.flag = this.lock.newCondition();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Login Screen");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.enterButton) {
            String sLevel = this.levelArea.getText();
            int iLevel;
            try {
                iLevel = Integer.parseInt(sLevel);
            } catch (NumberFormatException ex) {
                messages.setText("Level must be a number.");
                return;
            }
            String id = this.idArea.getText();
            if (id.equals(""))
            {
                this.messages.setText("Must enter ID.");
                return;
            }

            this.cred.setLevel(iLevel);
            this.cred.setId(id);
            lock.lock();
            flag.signal();
            lock.unlock();
        }
    }

    @Override
    public void run()
    {
        setVisible(true);
        this.lock.lock();
            try {
                this.flag.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        finally {
                setVisible(false);
                this.lock.unlock();
            }
    }
}
