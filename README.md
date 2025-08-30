# Hugs in a Bag Website - Developer README

## 🚀 Quick Start for New Team Members

Welcome to the Hugs in a Bag website development team! This guide will help you understand our website structure and how to make updates.

## 📁 File Structure

```
HugsInABag-website/
├── index.html              # Home page
├── about.html              # About Us & Team Members page
├── portfolio.html          # Our Work/Gallery page
├── get-involved.html       # Volunteer opportunities page
├── contact.html            # Contact information page
├── images/
│   └── HugsInABag-member-photos/   # Team member photos (firstname-lastname.jpg)
└── tools/
    ├── MemberCardGenerator.java     # Auto-generates team member cards
    └── HiaB-Team-Info - Sheet1.csv # Downloaded from Google Sheets
```

## 🛠️ How to Update Content

### 1. Updating Team Members (Automated Method)

**Step 1:** Download the latest CSV from Google Sheets
- Go to: https://docs.google.com/spreadsheets/d/1NkuFuSVa_5iLQlFsg63C-WsyWvCe_EMYNSIt49TKx3M/edit
- File → Download → Comma Separated Values (.csv)
- Save as `HiaB-Team-Info - Sheet1.csv` in the `tools/` folder

**Step 2:** Add member photos
- Name photos as `firstname-lastname.jpg` (all lowercase, no spaces)
- Example: "Jane Doe" → `jane-doe.jpg`
- Example: "Zhouyuan Wu(Emily)" → `zhouyuan-wu.jpg` (parentheses content removed)
- Place in `images/HugsInABag-member-photos/` folder
- Recommended: Square images, 400x400px, under 200KB

**Step 3:** Run the Java tool
```bash
cd tools
javac MemberCardGenerator.java
java MemberCardGenerator
```

**Step 4:** Check the console output
- ✓ = Photo found and linked
- ✗ = No photo found (will show 👤 emoji)

### 2. Manual Content Updates

#### Adding/Editing Static Content
Each HTML file has embedded CSS for easy editing. To update:
- Text content: Find the relevant `<section>` and edit the text
- Statistics: Look for `[150+]` placeholders in index.html
- Links: Update href attributes as needed

#### Important HTML Markers (DO NOT REMOVE)
In `about.html`, these markers are used by the Java tool:
```html
<!-- Team Members -->
<!-- Team members end -->
```

### 3. Adding New Pages

1. Copy an existing HTML file as a template
2. Update the `<title>` tag
3. Change the active navigation link:
   ```html
   <a href="newpage.html" class="nav-link active">New Page</a>
   ```
4. Add the new page link to ALL other HTML files' navigation

## 🎨 Styling Guide

### Color Palette
```css
--primary-yellow: #F4D06F;
--primary-coral: #D5896F;
--secondary-mint: #9DD9D2;
--secondary-beige: #DAB785;
--dark-blue: #04395E;
--darker-blue: #031D44;
--white: #FFFFFF;
--off-white: #FFF9F0;
```

### CSS Location
All CSS is embedded in each HTML file's `<style>` section for simplicity. Common styles are duplicated across files.

## 📝 Common Tasks

### Update Hospital/Volunteer Statistics
In `index.html`, find the "Impact Statistics" section:
```html
<h3>[150+]</h3>
<p>Active Volunteers</p>
<span>from [25] countries</span>
```

### Add Portfolio Images
In `portfolio.html`, add new gallery items:
```html
<div class="gallery-item" data-category="cards">
    <div class="gallery-image">
        <div class="placeholder-img"></div>
    </div>
    <div class="gallery-item-info">
        <h3>Item Title</h3>
        <p>Description</p>
        <span class="item-category">Category</span>
    </div>
</div>
```

### Update Contact Information
In `contact.html`, update placeholders:
- Email: `[email@placeholder.com]`
- Social media handles: `@[placeholder]`

### Add FAQ Questions
In `contact.html`, add new FAQ items following the existing pattern with the accordion structure.


## 📰 Updating Worklog/Blog Posts

### File Structure
```
worklog/
├── pdfs/
│   └── YYYY-MM-description.pdf      # PDF files with stories
└── images/
    └── YYYY-MM-description-preview.jpg   # Preview images (square recommended)
```

### How to Add a New Blog Post

1. **Prepare files:**
   - PDF: Name as `YYYY-MM-description.pdf` (e.g., `2024-03-crafting-session.pdf`)
   - Preview image: Same name + `-preview.jpg` (e.g., `2024-03-crafting-session-preview.jpg`)
   - Place in respective folders

2. **Edit worklog.html:**
   Copy this template and add before `</div>` of gallery-grid:
   ```html
   <div class="gallery-item" onclick="openPDF('worklog/pdfs/YYYY-MM-description.pdf', 'Your Title Here')">
       <div class="gallery-image">
           <img src="worklog/images/YYYY-MM-description-preview.jpg" alt="Preview description">
       </div>
       <div class="gallery-item-info">
           <h3>Your Title Here</h3>
           <p>Brief description of the blog post</p>
           <span class="item-category">Month Year</span>
       </div>
   </div>
   ```

3. **Replace:**
   - `YYYY-MM-description` with your actual filename
   - `Your Title Here` with the blog title (appears in lightbox)
   - `Brief description` with 1-2 line summary
   - `Month Year` with the date

## ⚠️ Important Notes

1. **Always test locally** before uploading changes
2. **Keep backups** before major updates
3. **Member photos** should be appropriately sized and named
4. **Don't edit** between the Team Members markers in about.html manually - use the Java tool
5. **Mobile responsiveness** is built-in - test on different screen sizes

## 🐛 Troubleshooting

### Java Tool Issues
- **"File not found"**: Check CSV filename matches exactly: `HiaB-Team-Info - Sheet1.csv`
- **"Could not find markers"**: Ensure HTML markers haven't been deleted from about.html
- **Photos not showing**: Check filename matches pattern (lowercase, hyphens, .jpg)

### Display Issues
- **Cards not in grid**: Check markers are inside `<div class="members-grid">`
- **Broken layout**: Validate HTML for unclosed tags
- **Images stretched**: Ensure photos are square or near-square ratio

## After Deploying Updates
If changes don't appear immediately:
1. Wait 5-10 minutes for GitHub Pages to update
2. Hard refresh: Ctrl+Shift+R (Windows) or Cmd+Shift+R (Mac)

## 📞 Need Help?

- Check existing code patterns in the HTML files
- Test changes locally first
- Keep the file structure organized
- Comment your code when adding complex features

## 🔄 Git Workflow (if using version control)

```bash
git pull                     # Get latest changes
# Make your edits
git add .                    # Stage changes
git commit -m "Update: description of changes"
git push                     # Upload changes
```

---