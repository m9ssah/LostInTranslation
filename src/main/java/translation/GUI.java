package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            String[] countryCodes = translator.getCountryCodes().toArray(new String[0]);
            String[] countries = new String[countryCodes.length];
            for (int i = 0; i < countryCodes.length; i++) {
                countries[i] = countryCodeConverter.fromCountryCode(countryCodes[i]);
            }
            JList<String> countryList = new JList<>(countries);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setLayoutOrientation(JList.VERTICAL);
            JScrollPane countryListScroller = new JScrollPane(countryList);
            countryListScroller.setPreferredSize(new Dimension(360, 150));
            countryPanel.add(countryListScroller);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            String[] languageCodes = translator.getLanguageCodes().toArray(new String[0]);
            String[] languages = new String[languageCodes.length];
            for (int i = 0; i < languageCodes.length; i++) {
                languages[i] = languageCodeConverter.fromLanguageCode(languageCodes[i]);
            }
            JComboBox<String> languageList = new JComboBox<>(languages);
            languageList.setSelectedIndex(0);
            languagePanel.add(languageList);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = (String) languageList.getSelectedItem();
                    String country = countryList.getSelectedValue();
                    String languageCode = languageCodeConverter.fromLanguage(language);
                    String countryCode = countryCodeConverter.fromCountry(country);

                    String result = translator.translate(countryCode, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
