import java.io.*;
import java.util.*;
/*This tool reads member info in members.csv to generate the code for member section of about.html, and stores it into member-cards-output.txt
Using guide:
1) download the latest csv update from https://docs.google.com/spreadsheets/d/1NkuFuSVa_5iLQlFsg63C-WsyWvCe_EMYNSIt49TKx3M/edit?gid=0#gid=0 and rename into members.csv
2) run MemberCardGenerator.java
3) manually check the generated member-cards-output.txt and paste into about.html
*/
public class MemberCardGenerator {
    private static final String CSV_PATH = "members.csv";  // In same folder as Java file
    private static final String HTML_PATH = "../about.html"; // Up one level
    private static final String TEST_HTML_PATH = "test.html"; // In same folder
    
    public static void main(String[] args) {
        try {
            // Choose which file to update
            boolean testMode = false; // Set to false when ready for production
            
            String targetHTML = testMode ? TEST_HTML_PATH : HTML_PATH;
            
            // Generate all member cards from CSV
            String allMemberCards = generateAllMemberCards(CSV_PATH);
            
            // Update the HTML file
            updateHTMLFile(targetHTML, allMemberCards);
            
            System.out.println("Updated file: " + targetHTML);
            System.out.println("HTML file updated successfully!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String generateAllMemberCards(String csvFile) throws IOException {
        StringBuilder allCards = new StringBuilder();
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        
        String line;
        boolean firstLine = true;
        
        while ((line = csvReader.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // Skip header row
                continue;
            }
            
            String[] data = parseCSVLine(line);
            
            if (data.length >= 9) {
                String memberCard = generateMemberCard(
                    data[0].trim(), // Full Name
                    data[2].trim(), // Team/Position
                    data[8].trim()  // Bio
                );
                allCards.append(memberCard).append("\n");
            }
        }
        
        csvReader.close();
        return allCards.toString();
    }
    
    private static void updateHTMLFile(String htmlFile, String newContent) throws IOException {
        // Read the entire HTML file
        StringBuilder fileContent = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(htmlFile));
        String line;
        
        while ((line = reader.readLine()) != null) {
            fileContent.append(line).append("\n");
        }
        reader.close();
        
        // Find the markers
        String startMarker = "<!-- Team Members -->";
        String endMarker = "<!--Team members end-->";
        
        int startIndex = fileContent.indexOf(startMarker);
        int endIndex = fileContent.indexOf(endMarker);
        
        if (startIndex == -1 || endIndex == -1) {
            System.err.println("Error: Could not find markers in HTML file!");
            return;
        }
        
        // Calculate where to insert new content (after the start marker)
        int insertStart = startIndex + startMarker.length();
        
        // Replace content between markers
        String beforeMarker = fileContent.substring(0, insertStart);
        String afterMarker = fileContent.substring(endIndex);
        
        // Build new file content - FIXED: Removed extra spaces
        String newFileContent = beforeMarker + "\n" + newContent + afterMarker;
        
        // Write back to file
        PrintWriter writer = new PrintWriter(new FileWriter(htmlFile));
        writer.print(newFileContent);
        writer.close();
    }
    
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        result.add(currentField.toString());
        
        return result.toArray(new String[0]);
    }
    
    private static String generateMemberCard(String name, String role, String bio) {
        // Escape HTML special characters
        name = escapeHtml(name);
        role = escapeHtml(role);
        bio = escapeHtml(bio);
        
        return String.format(
            "                <div class=\"member-card\">\n" +
            "                    <div class=\"member-photo\">\n" +
            "                        👤\n" +
            "                    </div>\n" +
            "                    <h3>%s</h3>\n" +
            "                    <p class=\"member-role\">%s</p>\n" +
            "                    <p class=\"member-bio\">%s</p>\n" +
            "                </div>",
            name, role, bio
        );
    }
    
    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
}