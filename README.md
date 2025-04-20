### Algorithm Visualizer
# 🚀 Git Branch Workflow for Team Members

Please follow these steps to ensure smooth collaboration and avoid conflicts. Everyone should work on their **own branch** — not directly on `main`.

---

## 📥 1. Pull the Latest Changes from `main`

Make sure you're working with the latest code:

```bash
git checkout main
git pull origin main
```

## 🌱 2. switch to your branch Branch and be updated with main
```bash
git checkout  your-branch-name
git merge main
```
## ✏️ 3. Make Changes and Commit
After editing files, stage and commit your changes:

```bash
git add .
git commit -m "Your descriptive commit message"
```
## ☁️ 4. Push Your Branch to Remote
Push your new branch to the remote repository:

```bash
git push origin your-branch-name
```