package ViewPackage;

import ControllerPackage.Controller;
import ViewPackage.CRUD.*;
import ViewPackage.Job.*;
import ViewPackage.Search.SearchFamilyPanel;
import ViewPackage.Search.SearchRentedBikePanel;
import ViewPackage.Search.SearchSubscriptionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    private final Container mainContainer, contentContainer, animationContainer;
    private final Controller controller;
    private final JMenuBar menuBar;
    // Menus
    private final JMenu crudMember;
    private final JMenu researches;
    //Animation
    private BikeAnimation bikeAnimation;

    public MainWindow() {
        super("Libia Vélo");
        setBounds(200, 200, 1200, 800);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        animationContainer = new JPanel(new BorderLayout());
        contentContainer = new JPanel(new BorderLayout());
        contentContainer.add(new WelcomePanel());
        contentContainer.setVisible(true);
        animationContainer.setVisible(true);

        mainContainer = getContentPane();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.add(contentContainer);
        mainContainer.add(animationContainer);
        mainContainer.setVisible(true);
        controller = new Controller();

        this.menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        crudMember = new JMenu("Membre");
        researches = new JMenu("Recherche");
        JMenu businessTask = new JMenu("Tâche métier");
        menuBar.add(crudMember);
        menuBar.add(researches);
        menuBar.add(businessTask);

        //CRUD
        setCrudMenu();

        //Research
        setSearchMenu();

        //BusinessTask
        JMenuItem nbRentTask = new JMenuItem("Locations par stations");
        businessTask.add(nbRentTask);
        nbRentTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new BusinessTaskPanel(contentContainer, controller));
                contentContainer.revalidate();
            }
        });

        //Bike animation
        setAnimation();

        this.setVisible(true);
        mainContainer.repaint();
        revalidate();
    }

    private void setCrudMenu() {
        // CRUD
        JMenuItem addMember = new JMenuItem("Ajouter un membre");
        JMenuItem memberList = new JMenuItem("Liste des membres");
        JMenuItem updateMember = new JMenuItem("Modifier ou supprimer un membre");
        crudMember.add(addMember);
        crudMember.add(memberList);
        crudMember.add(updateMember);
        addMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new AddMemberPanel(contentContainer, controller));
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
        memberList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new MemberListPanel(contentContainer, controller));
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
        updateMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new SearchMemberPanel(contentContainer, controller));
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });


    }
    private void setSearchMenu(){
        JMenuItem menuSearchRentedBikes = new JMenuItem("Recherche vélos loués");
        JMenuItem menuSearchSubscriptions = new JMenuItem("Recherche abonnements ");
        JMenuItem menuSearchFamilySub = new JMenuItem("Recherche abonnement familial");
        researches.add(menuSearchRentedBikes);
        researches.add(menuSearchSubscriptions);
        researches.add(menuSearchFamilySub);

        menuSearchFamilySub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new SearchFamilyPanel(controller, contentContainer));
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
        menuSearchSubscriptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new SearchSubscriptionPanel(controller, contentContainer));
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
        menuSearchRentedBikes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new SearchRentedBikePanel(controller));
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
    }
    private void setAnimation(){
        bikeAnimation = new BikeAnimation();
        JPanel bikePanel = new JPanel(new BorderLayout());
        bikePanel.setPreferredSize(new Dimension(1200, 200));
        bikePanel.add(bikeAnimation, BorderLayout.CENTER);
        animationContainer.add(bikePanel, BorderLayout.SOUTH);
    }

}
