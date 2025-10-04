# Hugs in a Bag Website - Developer README

## 🚀 Quick Start for New Team Members

Welcome! This guide will help you understand and maintain the Hugs in a Bag website.

## 📁 File Structure

```
HugsInABag-website/
├── index.html                    # Home page
├── about.html                    # About Us & Team Members
├── portfolio.html                # Our Work (products + behind-scenes)
├── worklog.html                  # Impact/Community (donations + testimonials)
├── why-it-matters.html           # Research articles listing
├── get-involved.html             # Volunteer opportunities
├── contact.html                  # Contact information
├── styles.css                    # ALL styles (shared across pages)
│
├── articles/                     # Individual article pages
│   ├── hospital-anxiety.html
│   ├── caregiver-support.html
│   ├── hospital-illness.html
│   ├── art-therapy.html
│   ├── childhood-cancer.html
│   └── cancer-survivor-journey.html
│
├── images/
│   ├── HiaB-logo.png            # Navigation logo
│   ├── hero.png                 # Homepage hero background
│   ├── HugsInABag-member-photos/
│   │   └── firstname-lastname.jpg  # Team photos (lowercase)
│   ├── comfort-kits/            # Product photos
│   ├── behind-scenes/           # Behind-the-scenes photos
│   └── articles/                # Article images
│       ├── art-therapy-cover.jpg
│       ├── art-therapy-1.jpg
│       └── Stress-stats.png
│
└── tools/                       # Team member update tools
    ├── MemberCardGenerator.java
    ├── HiaB-Team-Info - Sheet1.csv
    └── run-member-generator.bat
```

## 🎨 Design System

### Colors (defined in styles.css :root)
- **Primary Pink**: `#ffafcc` (signature color)
- **Hot Pink**: `#ff90b3` (hover states)
- **Sky Blue**: `#bde0fe` (accents)
- **Light Pink**: `#ffc8dd` (backgrounds)
- **Text**: `#2d3436` (dark gray for readability)

### Common CSS Classes
- `.stat-card` - Statistics boxes
- `.team-card` - Info cards with hover effect
- `.story-card` - Quote/testimonial boxes
- `.gallery-item` - Portfolio/article preview cards
- `.cta-button` - Call-to-action buttons

## 👥 Updating Team Members (Automated)

### Step 1: Update Google Sheets
- Go to the [team spreadsheet](https://docs.google.com/spreadsheets/d/1NkuFuSVa_5iLQlFsg63C-WsyWvCe_EMYNSIt49TKx3M/edit)
- Add/edit member information
- Download as CSV (keep filename: `HiaB-Team-Info - Sheet1.csv`)

### Step 2: Add Photos
- Name photos as `firstname-lastname.jpg` (all lowercase)
- Example: "Jane Doe" → `jane-doe.jpg`
- Place in `images/HugsInABag-member-photos/`

### Step 3: Run Update Tool
- Double-click `run-member-generator.bat` in tools folder
- Check console for any missing photos
- Refresh about.html to see changes

## 📝 Adding New Articles

### Step 1: Create Article HTML
1. Copy any existing article from `articles/` folder as template
2. Update:
   - Title in `<title>` and `<h1>` tags
   - Article content in the main section
   - Author name at bottom

### Step 2: Add Preview Card
In `why-it-matters.html`, add before closing `</div>` of gallery-grid:
```html
<a href="articles/your-article.html" class="gallery-item" style="text-decoration: none; color: inherit;">
    <div class="gallery-image" style="background-color: var(--secondary-mint); display: flex; align-items: center; justify-content: center;">
        <span style="font-size: 4rem;">📄</span>
    </div>
    <div class="gallery-item-info">
        <h3>Article Title</h3>
        <p>Brief description...</p>
        <span class="item-category">Continue Reading →</span>
    </div>
</a>
```

## 🖼️ Adding Portfolio/Behind-Scenes Photos

### Portfolio Products
In `portfolio.html`, find the gallery-grid and add:
```html
<div class="gallery-item" data-category="cards">
    <div class="gallery-image">
        <img src="images/comfort-kits/your-image.jpg" alt="Description">
    </div>
    <div class="gallery-item-info">
        <h3>Item Name</h3>
        <p>Description</p>
        <span class="item-category">Category</span>
    </div>
</div>
```

### Behind-the-Scenes
In `portfolio.html`, find the photo-grid section and add:
```html
<div class="photo-item">
    <img src="images/behind-scenes/your-photo.jpg" alt="Description">
    <div class="photo-caption">Caption text</div>
</div>
```

## 🔄 Common Updates

### Update Statistics (index.html)
Find and update numbers in square brackets:
```html
<h4>[150+]</h4>  <!-- Change to new number -->
<p>Active Volunteers</p>
```

### Add Navigation Links
When adding new pages, update the nav menu in ALL HTML files:
```html
<a href="newpage.html" class="nav-link">New Page</a>
```

## ⚠️ Important Rules

1. **DON'T edit between these markers in about.html:**
   ```html
   <!-- Team Members -->
   <!-- Team members end -->
   ```
   Use the Java tool instead!

2. **ALL styles go in styles.css** - no `<style>` tags in HTML files

3. **Image naming conventions:**
   - Team photos: `firstname-lastname.jpg` (lowercase)
   - Keep images under 500KB for fast loading
   - Use descriptive names for other images

4. **Test before uploading:**
   - Check all links work
   - Verify images display correctly
   - Test on mobile (responsive design)

## 🐛 Troubleshooting

### Java Tool Issues
- **"File not found"**: Ensure CSV is named exactly `HiaB-Team-Info - Sheet1.csv`
- **Photos not showing**: Check filename matches (lowercase, no spaces)

### Layout Issues
- **Broken grid**: Check for unclosed `<div>` tags
- **Missing styles**: Ensure `<link rel="stylesheet" href="styles.css">` is in `<head>`

### Article Issues
- **Links not working**: Check file paths use `../` for parent directory
- **Images not showing**: Verify path starts with `../images/`

## 📞 Need Help?

1. Check existing code for examples
2. Keep backups before major changes
3. Ask team lead if unsure
4. Remember: The site should feel warm and handcrafted, not corporate!

## 🔄 Git Workflow (if using version control)

```bash
git pull                     # Get latest changes
# Make your edits
git add .                    # Stage changes
git commit -m "Update: description of changes"
git push                     # Upload changes
```

---