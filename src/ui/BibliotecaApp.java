package ui;

import model.Abonament;
import model.Autor;
import model.Bibliotecar;
import model.Carte;
import model.CarteDigitala;
import model.CarteFizica;
import model.Cititor;
import model.Imprumut;
import model.Penalizare;
import model.Rezervare;
import model.Sectiune;
import service.BibliotecaJdbcService;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

public class BibliotecaApp extends JFrame {
    private final BibliotecaJdbcService service = new BibliotecaJdbcService();

    private DefaultTableModel cartiModel;
    private DefaultTableModel cititoriModel;
    private DefaultTableModel bibliotecariModel;
    private DefaultTableModel imprumuturiModel;
    private DefaultTableModel rezervariModel;
    private DefaultTableModel penalizariModel;
    private DefaultTableModel autoriModel;
    private DefaultTableModel sectiuniModel;
    private DefaultTableModel abonamenteModel;

    private JComboBox<NamedItem<Autor>> autorCarteBox;
    private JComboBox<NamedItem<Sectiune>> sectiuneCarteBox;
    private JComboBox<NamedItem<Abonament>> abonamentCititorBox;
    private JComboBox<NamedItem<Cititor>> cititorImprumutBox;
    private JComboBox<NamedItem<Bibliotecar>> bibliotecarImprumutBox;
    private JComboBox<NamedItem<Carte>> carteImprumutBox;
    private JComboBox<NamedItem<Cititor>> cititorRezervareBox;
    private JComboBox<NamedItem<Carte>> carteRezervareBox;
    private JComboBox<NamedItem<Imprumut>> imprumutPenalizareBox;

    public BibliotecaApp() {
        super("Biblioteca - Administrare");
        initializeService();
        buildFrame();
        refreshAll();
    }

    private void initializeService() {
        if (!service.initialize()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Conexiunea la baza de date nu a putut fi realizata.",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void buildFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 680));
        setSize(1180, 760);
        setLocationRelativeTo(null);
        add(createHeader(), BorderLayout.NORTH);
        add(createTabs(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 26, 18, 26));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Biblioteca");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(31, 41, 55));

        JLabel subtitle = new JLabel("Administrare carti, cititori, imprumuturi si rezervari");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(100, 116, 139));

        JPanel textPanel = new JPanel(new BorderLayout(0, 4));
        textPanel.setOpaque(false);
        textPanel.add(title, BorderLayout.NORTH);
        textPanel.add(subtitle, BorderLayout.SOUTH);
        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    private JTabbedPane createTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Catalog", createCatalogView());
        tabs.addTab("Persoane", createPeopleView());
        tabs.addTab("Imprumuturi", createLoanView());
        tabs.addTab("Rezervari", createReservationView());
        tabs.addTab("Penalizari", createPenaltyView());
        tabs.addTab("Date auxiliare", createAuxiliaryView());
        return tabs;
    }

    private JPanel createCatalogView() {
        cartiModel = tableModel("ID", "Titlu", "Autor", "Sectiune", "An", "Tip", "Detalii", "Disponibila");
        JTable table = table(cartiModel);

        JTextField idField = input();
        JTextField titluField = input();
        autorCarteBox = new JComboBox<>();
        sectiuneCarteBox = new JComboBox<>();
        JTextField anField = input();
        JComboBox<String> tipBox = new JComboBox<>(new String[]{"FIZICA", "DIGITALA"});
        JTextField detaliiField = input();

        JButton addButton = primaryButton("Adauga carte");
        addButton.addActionListener(event -> {
            try {
                Carte carte = createCarteFromFields(idField, titluField, autorCarteBox, sectiuneCarteBox, anField, tipBox, detaliiField);
                service.adaugaCarte(carte);
                clear(idField, titluField, anField, detaliiField);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton findButton = primaryButton("Cauta ID");
        findButton.addActionListener(event -> {
            try {
                Carte carte = service.cautaCarteDupaId(parseInt(idField, "ID carte"));
                if (carte == null) {
                    showError("Nu exista carte cu acest ID.");
                    return;
                }
                fillCarteForm(carte, idField, titluField, autorCarteBox, sectiuneCarteBox, anField, tipBox, detaliiField);
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton updateButton = primaryButton("Actualizeaza");
        updateButton.addActionListener(event -> {
            try {
                service.actualizeazaCarte(createCarteFromFields(idField, titluField, autorCarteBox, sectiuneCarteBox, anField, tipBox, detaliiField));
                refreshAll();
                showInfo("Cartea a fost actualizata.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton deleteButton = primaryButton("Sterge");
        deleteButton.addActionListener(event -> {
            try {
                service.stergeCarte(parseInt(idField, "ID carte"));
                clear(idField, titluField, anField, detaliiField);
                refreshAll();
                showInfo("Cartea a fost stearsa daca nu este folosita in alte tabele.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel form = formPanel();
        addRow(form, 0, "ID", idField, "Titlu", titluField);
        addRow(form, 1, "Autor", autorCarteBox, "Sectiune", sectiuneCarteBox);
        addRow(form, 2, "An", anField, "Tip", tipBox);
        addRow(form, 3, "Nr. pagini / MB", detaliiField, "", buttonPanel(addButton, findButton, updateButton, deleteButton));
        return view(table, form);
    }

    private JPanel createPeopleView() {
        cititoriModel = tableModel("ID", "Nume", "Email", "Abonament");
        bibliotecariModel = tableModel("ID", "Nume", "Email", "ID angajat", "Salariu");

        JTextField cititorId = input();
        JTextField cititorNume = input();
        JTextField cititorEmail = input();
        abonamentCititorBox = new JComboBox<>();
        JButton addCititor = primaryButton("Adauga cititor");
        addCititor.addActionListener(event -> {
            try {
                service.adaugaCititor(createCititorFromFields(cititorId, cititorNume, cititorEmail, abonamentCititorBox));
                clear(cititorId, cititorNume, cititorEmail);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton findCititor = primaryButton("Cauta ID");
        findCititor.addActionListener(event -> {
            try {
                Cititor cititor = service.cautaCititorDupaId(parseInt(cititorId, "ID cititor"));
                if (cititor == null) {
                    showError("Nu exista cititor cu acest ID.");
                    return;
                }
                cititorNume.setText(cititor.getNume());
                cititorEmail.setText(cititor.getEmail());
                selectComboById(abonamentCititorBox, cititor.getAbonament().getId(), Abonament::getId);
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton updateCititor = primaryButton("Actualizeaza");
        updateCititor.addActionListener(event -> {
            try {
                service.actualizeazaCititor(createCititorFromFields(cititorId, cititorNume, cititorEmail, abonamentCititorBox));
                refreshAll();
                showInfo("Cititorul a fost actualizat.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton deleteCititor = primaryButton("Sterge");
        deleteCititor.addActionListener(event -> {
            try {
                service.stergeCititor(parseInt(cititorId, "ID cititor"));
                clear(cititorId, cititorNume, cititorEmail);
                refreshAll();
                showInfo("Cititorul a fost sters daca nu este folosit in alte tabele.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel cititorForm = formPanel();
        addRow(cititorForm, 0, "ID", cititorId, "Nume", cititorNume);
        addRow(cititorForm, 1, "Email", cititorEmail, "Abonament", abonamentCititorBox);
        addRow(cititorForm, 2, "", new JLabel(), "", buttonPanel(addCititor, findCititor, updateCititor, deleteCititor));

        JTextField bibliotecarId = input();
        JTextField bibliotecarNume = input();
        JTextField bibliotecarEmail = input();
        JTextField idAngajat = input();
        JTextField salariu = input();
        JButton addBibliotecar = primaryButton("Adauga bibliotecar");
        addBibliotecar.addActionListener(event -> {
            try {
                Bibliotecar bibliotecar = new Bibliotecar(
                        parseInt(bibliotecarId, "ID bibliotecar"),
                        bibliotecarNume.getText(),
                        bibliotecarEmail.getText(),
                        idAngajat.getText(),
                        parseDouble(salariu, "Salariu")
                );
                service.adaugaBibliotecar(bibliotecar);
                clear(bibliotecarId, bibliotecarNume, bibliotecarEmail, idAngajat, salariu);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel bibliotecarForm = formPanel();
        addRow(bibliotecarForm, 0, "ID", bibliotecarId, "Nume", bibliotecarNume);
        addRow(bibliotecarForm, 1, "Email", bibliotecarEmail, "ID angajat", idAngajat);
        addRow(bibliotecarForm, 2, "Salariu", salariu, "", addBibliotecar);

        JPanel content = verticalPanel();
        content.add(section("Cititori", table(cititoriModel), cititorForm));
        content.add(section("Bibliotecari", table(bibliotecariModel), bibliotecarForm));
        return content;
    }

    private JPanel createLoanView() {
        imprumuturiModel = tableModel("ID", "Cititor", "Bibliotecar", "Carte", "Data imprumut", "Data returnare", "Activ");
        JTable table = table(imprumuturiModel);

        JTextField idField = input();
        cititorImprumutBox = new JComboBox<>();
        bibliotecarImprumutBox = new JComboBox<>();
        carteImprumutBox = new JComboBox<>();
        JTextField dataReturnare = input();
        dataReturnare.setText(LocalDate.now().plusDays(14).toString());

        JButton addButton = primaryButton("Creeaza imprumut");
        addButton.addActionListener(event -> {
            try {
                Imprumut imprumut = new Imprumut(
                        parseInt(idField, "ID imprumut"),
                        selectedValue(cititorImprumutBox, "Selecteaza un cititor."),
                        selectedValue(bibliotecarImprumutBox, "Selecteaza un bibliotecar."),
                        selectedValue(carteImprumutBox, "Selecteaza o carte."),
                        LocalDate.now(),
                        LocalDate.parse(dataReturnare.getText().trim()),
                        true
                );
                service.adaugaImprumut(imprumut);
                clear(idField);
                refreshAll();
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });

        JPanel form = formPanel();
        addRow(form, 0, "ID", idField, "Cititor", cititorImprumutBox);
        addRow(form, 1, "Bibliotecar", bibliotecarImprumutBox, "Carte", carteImprumutBox);
        addRow(form, 2, "Returnare", dataReturnare, "", addButton);
        return view(table, form);
    }

    private JPanel createReservationView() {
        rezervariModel = tableModel("ID", "Cititor", "Carte", "Data", "Status");
        JTable table = table(rezervariModel);

        JTextField idField = input();
        cititorRezervareBox = new JComboBox<>();
        carteRezervareBox = new JComboBox<>();
        JTextField status = input();
        status.setText("Activa");

        JButton addButton = primaryButton("Adauga rezervare");
        addButton.addActionListener(event -> {
            try {
                Rezervare rezervare = new Rezervare(
                        parseInt(idField, "ID rezervare"),
                        selectedValue(cititorRezervareBox, "Selecteaza un cititor."),
                        selectedValue(carteRezervareBox, "Selecteaza o carte."),
                        LocalDate.now(),
                        status.getText()
                );
                service.adaugaRezervare(rezervare);
                clear(idField);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel form = formPanel();
        addRow(form, 0, "ID", idField, "Cititor", cititorRezervareBox);
        addRow(form, 1, "Carte", carteRezervareBox, "Status", status);
        addRow(form, 2, "", new JLabel(), "", addButton);
        return view(table, form);
    }

    private JPanel createPenaltyView() {
        penalizariModel = tableModel("ID", "Cititor", "Imprumut", "Suma", "Motiv", "Platita", "Data");
        JTable table = table(penalizariModel);

        JTextField idField = input();
        imprumutPenalizareBox = new JComboBox<>();
        JTextField suma = input();
        JTextField motiv = input();
        motiv.setText("Intarziere la returnare");

        JButton addButton = primaryButton("Adauga penalizare");
        addButton.addActionListener(event -> {
            try {
                Imprumut imprumut = selectedValue(imprumutPenalizareBox, "Selecteaza un imprumut.");
                Penalizare penalizare = new Penalizare(
                        parseInt(idField, "ID penalizare"),
                        imprumut.getCititor(),
                        imprumut,
                        parseDouble(suma, "Suma"),
                        motiv.getText(),
                        false,
                        LocalDate.now()
                );
                service.adaugaPenalizare(penalizare);
                clear(idField, suma);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel form = formPanel();
        addRow(form, 0, "ID", idField, "Imprumut", imprumutPenalizareBox);
        addRow(form, 1, "Suma", suma, "Motiv", motiv);
        addRow(form, 2, "", new JLabel(), "", addButton);
        return view(table, form);
    }

    private JPanel createAuxiliaryView() {
        autoriModel = tableModel("ID", "Nume", "Nationalitate");
        sectiuniModel = tableModel("ID", "Nume");
        abonamenteModel = tableModel("ID", "Tip", "Pret", "Durata luni", "Max imprumuturi");

        JTextField autorId = input();
        JTextField autorNume = input();
        JTextField nationalitate = input();
        JButton addAutor = primaryButton("Adauga autor");
        addAutor.addActionListener(event -> {
            try {
                service.adaugaAutor(new Autor(parseInt(autorId, "ID autor"), autorNume.getText(), nationalitate.getText()));
                clear(autorId, autorNume, nationalitate);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton findAutor = primaryButton("Cauta ID");
        findAutor.addActionListener(event -> {
            try {
                Autor autor = service.cautaAutorDupaId(parseInt(autorId, "ID autor"));
                if (autor == null) {
                    showError("Nu exista autor cu acest ID.");
                    return;
                }
                autorNume.setText(autor.getNume());
                nationalitate.setText(autor.getNationalitate());
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton updateAutor = primaryButton("Actualizeaza");
        updateAutor.addActionListener(event -> {
            try {
                service.actualizeazaAutor(new Autor(parseInt(autorId, "ID autor"), autorNume.getText(), nationalitate.getText()));
                refreshAll();
                showInfo("Autorul a fost actualizat.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton deleteAutor = primaryButton("Sterge");
        deleteAutor.addActionListener(event -> {
            try {
                service.stergeAutor(parseInt(autorId, "ID autor"));
                clear(autorId, autorNume, nationalitate);
                refreshAll();
                showInfo("Autorul a fost sters daca nu este folosit de carti.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel autorForm = formPanel();
        addRow(autorForm, 0, "ID", autorId, "Nume", autorNume);
        addRow(autorForm, 1, "Nationalitate", nationalitate, "", buttonPanel(addAutor, findAutor, updateAutor, deleteAutor));

        JTextField sectiuneId = input();
        JTextField sectiuneNume = input();
        JButton addSectiune = primaryButton("Adauga sectiune");
        addSectiune.addActionListener(event -> {
            try {
                service.adaugaSectiune(new Sectiune(parseInt(sectiuneId, "ID sectiune"), sectiuneNume.getText()));
                clear(sectiuneId, sectiuneNume);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton findSectiune = primaryButton("Cauta ID");
        findSectiune.addActionListener(event -> {
            try {
                Sectiune sectiune = service.cautaSectiuneDupaId(parseInt(sectiuneId, "ID sectiune"));
                if (sectiune == null) {
                    showError("Nu exista sectiune cu acest ID.");
                    return;
                }
                sectiuneNume.setText(sectiune.getNume());
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton updateSectiune = primaryButton("Actualizeaza");
        updateSectiune.addActionListener(event -> {
            try {
                service.actualizeazaSectiune(new Sectiune(parseInt(sectiuneId, "ID sectiune"), sectiuneNume.getText()));
                refreshAll();
                showInfo("Sectiunea a fost actualizata.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
        JButton deleteSectiune = primaryButton("Sterge");
        deleteSectiune.addActionListener(event -> {
            try {
                service.stergeSectiune(parseInt(sectiuneId, "ID sectiune"));
                clear(sectiuneId, sectiuneNume);
                refreshAll();
                showInfo("Sectiunea a fost stearsa daca nu este folosita de carti.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel sectiuneForm = formPanel();
        addRow(sectiuneForm, 0, "ID", sectiuneId, "Nume", sectiuneNume);
        addRow(sectiuneForm, 1, "", new JLabel(), "", buttonPanel(addSectiune, findSectiune, updateSectiune, deleteSectiune));

        JTextField abonamentId = input();
        JTextField tip = input();
        JTextField pret = input();
        JTextField durata = input();
        JTextField max = input();
        JButton addAbonament = primaryButton("Adauga abonament");
        addAbonament.addActionListener(event -> {
            try {
                service.adaugaAbonament(new Abonament(
                        parseInt(abonamentId, "ID abonament"),
                        tip.getText(),
                        parseDouble(pret, "Pret"),
                        parseInt(durata, "Durata"),
                        parseInt(max, "Max imprumuturi")
                ));
                clear(abonamentId, tip, pret, durata, max);
                refreshAll();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        JPanel abonamentForm = formPanel();
        addRow(abonamentForm, 0, "ID", abonamentId, "Tip", tip);
        addRow(abonamentForm, 1, "Pret", pret, "Durata", durata);
        addRow(abonamentForm, 2, "Max", max, "", addAbonament);

        JPanel content = verticalPanel();
        content.add(section("Autori", table(autoriModel), autorForm));
        content.add(section("Sectiuni", table(sectiuniModel), sectiuneForm));
        content.add(section("Abonamente", table(abonamenteModel), abonamentForm));
        return content;
    }

    private JPanel view(JTable table, JPanel form) {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(new Color(246, 247, 249));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(form, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel section(String title, JTable table, JPanel form) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(920, 145));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(form, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel verticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(new Color(246, 247, 249));
        return panel;
    }

    private JPanel formPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private void addRow(JPanel panel, int row, String firstLabel, java.awt.Component firstInput, String secondLabel, java.awt.Component secondInput) {
        addComponent(panel, new JLabel(firstLabel), 0, row, 0.0);
        addComponent(panel, firstInput, 1, row, 1.0);
        addComponent(panel, new JLabel(secondLabel), 2, row, 0.0);
        addComponent(panel, secondInput, 3, row, 1.0);
    }

    private void addComponent(JPanel panel, java.awt.Component component, int x, int y, double weightX) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.weightx = weightX;
        constraints.insets = new Insets(5, 6, 5, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, constraints);
    }

    private JTable table(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        return table;
    }

    private DefaultTableModel tableModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTextField input() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(170, 30));
        return field;
    }

    private JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(170, 32));
        return button;
    }

    private JPanel buttonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        panel.setOpaque(false);
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private Carte createCarteFromFields(
            JTextField idField,
            JTextField titluField,
            JComboBox<NamedItem<Autor>> autorBox,
            JComboBox<NamedItem<Sectiune>> sectiuneBox,
            JTextField anField,
            JComboBox<String> tipBox,
            JTextField detaliiField
    ) {
        Autor autor = selectedValue(autorBox, "Selecteaza un autor.");
        Sectiune sectiune = selectedValue(sectiuneBox, "Selecteaza o sectiune.");
        String tip = (String) tipBox.getSelectedItem();
        int id = parseInt(idField, "ID carte");
        int anPublicatie = parseInt(anField, "An publicatie");
        int detalii = parseInt(detaliiField, "Detalii carte");

        if ("FIZICA".equals(tip)) {
            return new CarteFizica(id, titluField.getText(), autor, sectiune, anPublicatie, true, detalii);
        }

        return new CarteDigitala(id, titluField.getText(), autor, sectiune, anPublicatie, true, detalii);
    }

    private void fillCarteForm(
            Carte carte,
            JTextField idField,
            JTextField titluField,
            JComboBox<NamedItem<Autor>> autorBox,
            JComboBox<NamedItem<Sectiune>> sectiuneBox,
            JTextField anField,
            JComboBox<String> tipBox,
            JTextField detaliiField
    ) {
        idField.setText(String.valueOf(carte.getId()));
        titluField.setText(carte.getTitlu());
        selectComboById(autorBox, carte.getAutor().getId(), Autor::getId);
        selectComboById(sectiuneBox, carte.getSectiune().getId(), Sectiune::getId);
        anField.setText(String.valueOf(carte.getAnPublicatie()));

        if (carte instanceof CarteFizica carteFizica) {
            tipBox.setSelectedItem("FIZICA");
            detaliiField.setText(String.valueOf(carteFizica.getNrPagini()));
        } else if (carte instanceof CarteDigitala carteDigitala) {
            tipBox.setSelectedItem("DIGITALA");
            detaliiField.setText(String.valueOf(carteDigitala.getMarimeMB()));
        }
    }

    private Cititor createCititorFromFields(
            JTextField idField,
            JTextField numeField,
            JTextField emailField,
            JComboBox<NamedItem<Abonament>> abonamentBox
    ) {
        return new Cititor(
                parseInt(idField, "ID cititor"),
                numeField.getText(),
                emailField.getText(),
                selectedValue(abonamentBox, "Selecteaza un abonament.")
        );
    }

    private void refreshAll() {
        refreshTables();
        refreshCombos();
    }

    private void refreshTables() {
        setRows(abonamenteModel, service.getAbonamente(), abonament -> new Object[]{
                abonament.getId(), abonament.getTip(), abonament.getPret(), abonament.getDurataLuni(), abonament.getMaxImprumuturi()
        });
        setRows(autoriModel, service.getAutori(), autor -> new Object[]{
                autor.getId(), autor.getNume(), autor.getNationalitate()
        });
        setRows(sectiuniModel, service.getSectiuni(), sectiune -> new Object[]{
                sectiune.getId(), sectiune.getNume()
        });
        setRows(cititoriModel, service.getCititori(), cititor -> new Object[]{
                cititor.getId(), cititor.getNume(), cititor.getEmail(), cititor.getTipAbonament()
        });
        setRows(bibliotecariModel, service.getBibliotecari(), bibliotecar -> new Object[]{
                bibliotecar.getId(), bibliotecar.getNume(), bibliotecar.getEmail(), bibliotecar.getIdAngajat(), bibliotecar.getSalariu()
        });
        setRows(cartiModel, service.getCarti(), carte -> new Object[]{
                carte.getId(), carte.getTitlu(), carte.getAutor().getNume(), carte.getSectiune().getNume(),
                carte.getAnPublicatie(), getTipCarte(carte), getDetaliiCarte(carte), carte.getDisponibila() ? "Da" : "Nu"
        });
        setRows(imprumuturiModel, service.getImprumuturi(), imprumut -> new Object[]{
                imprumut.getId(), imprumut.getCititor().getNume(), imprumut.getBibliotecar().getNume(),
                imprumut.getCarte().getTitlu(), imprumut.getDataImprumut(), imprumut.getDataReturnare(),
                imprumut.getActiv() ? "Da" : "Nu"
        });
        setRows(rezervariModel, service.getRezervari(), rezervare -> new Object[]{
                rezervare.getId(), rezervare.getCititor().getNume(), rezervare.getCarte().getTitlu(),
                rezervare.getDataRezervare(), rezervare.getStatus()
        });
        setRows(penalizariModel, service.getPenalizari(), penalizare -> new Object[]{
                penalizare.getId(), penalizare.getCititor().getNume(), penalizare.getImprumut().getId(),
                penalizare.getSuma(), penalizare.getMotiv(), penalizare.isPlatita() ? "Da" : "Nu",
                penalizare.getDataPenalizare()
        });
    }

    private void refreshCombos() {
        setComboItems(autorCarteBox, service.getAutori(), autor -> autor.getId() + " - " + autor.getNume());
        setComboItems(sectiuneCarteBox, service.getSectiuni(), sectiune -> sectiune.getId() + " - " + sectiune.getNume());
        setComboItems(abonamentCititorBox, service.getAbonamente(), abonament -> abonament.getId() + " - " + abonament.getTip());
        setComboItems(cititorImprumutBox, service.getCititori(), cititor -> cititor.getId() + " - " + cititor.getNume());
        setComboItems(bibliotecarImprumutBox, service.getBibliotecari(), bibliotecar -> bibliotecar.getId() + " - " + bibliotecar.getNume());
        setComboItems(carteImprumutBox, service.getCarti(), carte -> carte.getId() + " - " + carte.getTitlu());
        setComboItems(cititorRezervareBox, service.getCititori(), cititor -> cititor.getId() + " - " + cititor.getNume());
        setComboItems(carteRezervareBox, service.getCarti(), carte -> carte.getId() + " - " + carte.getTitlu());
        setComboItems(imprumutPenalizareBox, service.getImprumuturi(), imprumut -> imprumut.getId() + " - " + imprumut.getCarte().getTitlu());
    }

    private <T> void setRows(DefaultTableModel model, java.util.List<T> items, RowMapper<T> mapper) {
        if (model == null) {
            return;
        }

        model.setRowCount(0);
        for (T item : items) {
            model.addRow(mapper.toRow(item));
        }
    }

    private <T> void setComboItems(JComboBox<NamedItem<T>> comboBox, java.util.List<T> items, TextMapper<T> mapper) {
        if (comboBox == null) {
            return;
        }

        comboBox.removeAllItems();
        for (T item : items) {
            comboBox.addItem(new NamedItem<>(item, mapper.map(item)));
        }
    }

    private <T> T selectedValue(JComboBox<NamedItem<T>> comboBox, String message) {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex < 0) {
            throw new IllegalArgumentException(message);
        }
        NamedItem<T> item = comboBox.getItemAt(selectedIndex);
        return item.value();
    }

    private <T> void selectComboById(JComboBox<NamedItem<T>> comboBox, int id, IdExtractor<T> extractor) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            NamedItem<T> item = comboBox.getItemAt(i);
            if (extractor.getId(item.value()) == id) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private int parseInt(JTextField field, String name) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " trebuie sa fie numar intreg.");
        }
    }

    private double parseDouble(JTextField field, String name) {
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " trebuie sa fie numar.");
        }
    }

    private void clear(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private String getTipCarte(Carte carte) {
        if (carte instanceof CarteFizica) {
            return "Fizica";
        }
        if (carte instanceof CarteDigitala) {
            return "Digitala";
        }
        return "Necunoscut";
    }

    private String getDetaliiCarte(Carte carte) {
        if (carte instanceof CarteFizica carteFizica) {
            return carteFizica.getNrPagini() + " pagini";
        }
        if (carte instanceof CarteDigitala carteDigitala) {
            return carteDigitala.getMarimeMB() + " MB";
        }
        return "";
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void dispose() {
        service.close();
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new BibliotecaApp().setVisible(true);
        });
    }

    private record NamedItem<T>(T value, String label) {
        @Override
        public String toString() {
            return label;
        }
    }

    @FunctionalInterface
    private interface RowMapper<T> {
        Object[] toRow(T value);
    }

    @FunctionalInterface
    private interface TextMapper<T> {
        String map(T value);
    }

    @FunctionalInterface
    private interface IdExtractor<T> {
        int getId(T value);
    }
}
