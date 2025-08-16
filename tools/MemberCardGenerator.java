import java.io.*;
import java.util.*;
/*This tool reads member info in HiaB-Team-Info - Sheet1.csv to generate the code for member section of about.html
Using guide:
1) download the latest csv update from https://docs.google.com/spreadsheets/d/1NkuFuSVa_5iLQlFsg63C-WsyWvCe_EMYNSIt49TKx3M/edit?gid=0#gid=0 
   (no need to rename - keep as "HiaB-Team-Info - Sheet1.csv")
2) ensure member photos are in ../images/HugsInABag-member-photos/ folder with naming pattern: firstname-lastname.jpg
3) run MemberCardGenerator.java
4) HTML will be automatically updated with member cards and photos
*/
public class MemberCardGenerator {
    private static final String CSV_PATH = "HiaB-Team-Info - Sheet1.csv";  // Updated CSV filename
    private static final String HTML_PATH = "../about.html"; // Up one level
    private static final String TEST_HTML_PATH = "test.html"; // In same folder
    private static final String IMAGES_FOLDER = "../images/HugsInABag-member-photos/"; // Updated folder name
    
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
        
        // Build new file content
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
        // Escape HTML special characters FIRST (before using for filename)
        String escapedName = escapeHtml(name);
        String escapedRole = escapeHtml(role);
        String escapedBio = escapeHtml(bio);
        
        // Remove parentheses and their contents for filename generation
        String cleanName = name.replaceAll("\\s*\\([^)]*\\)", "").trim();
        
        // Generate image filename based on cleaned name
        String imageFilename = cleanName.toLowerCase()
            .replace(" ", "-")
            .replace(".", "")
            .replace("'", "")
            .replace("&", "and")
            .replace(",", "")
            + ".jpg";
        
        // Check if image exists
        File imageFile = new File(IMAGES_FOLDER + imageFilename);
        String photoContent;
        
        if (imageFile.exists()) {
            // Use the image path relative to the HTML file location
            String imagePath = "images/HugsInABag-member-photos/" + imageFilename;
            photoContent = String.format("<img src=\"%s\" alt=\"%s\">", imagePath, escapedName);
            System.out.println("✓ Found image for " + name + ": " + imageFilename);
        } else {
            photoContent = "👤"; // Default emoji if no photo
            System.out.println("✗ No image found for " + name + " (looked for: " + imageFilename + ")");
        }
        
        return String.format(
            "                <div class=\"member-card\">\n" +
            "                    <div class=\"member-photo\">\n" +
            "                        %s\n" +
            "                    </div>\n" +
            "                    <h3>%s</h3>\n" +
            "                    <p class=\"member-role\">%s</p>\n" +
            "                    <p class=\"member-bio\">%s</p>\n" +
            "                </div>",
            photoContent, escapedName, escapedRole, escapedBio
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