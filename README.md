# 🧭 Pathfinding Visualizer

An interactive JavaFX-based pathfinding visualizer that demonstrates algorithms like Dijkstra’s, BFS, and DFS. The visualizer includes direction-aware movement, weighted cells, and real-time animation for educational or debugging purposes.

## 🚀 Features
- Visualize pathfinding algorithms step-by-step
- Direction-based movement penalties
- Weighted cell handling
- Real-time interaction and pausing
- Customizable grid with walls, weights, and more

## 🖥️ Requirements

Before running the project, ensure you have the following installed:

- [Java Development Kit (JDK 21+ with JavaFX)](https://www.azul.com/downloads/?version=java-21-lts&package=jdk-fx#zulu)
- IDE: VS Code or IntelliJ IDEA

## 🗂️ Project Structure
``` bash
libs/ → External libraries
│ ├── controlsfx-11.0.1.jar
│ └── jfoenix-9.0.8.jar

src/
├── Main/
│ ├── Algorithms/ → Pathfinding algorithms
│ │ ├── Algorithm.java
│ │ ├── AStar.java
│ │ └── Dijkstra.java
│ ├── Animation/ → JavaFX animations
│ │ ├── BounceIn.java
│ │ └── CustomAnimation.java
│ ├── Configurations/ → Constants and settings
│ │ └── Constants.java
│ ├── GraphRelated/ → Grid logic, cell states, directions
│ │ └── (e.g., Cell.java, CellState.java)
│ ├── Controller.java → Main UI logic
│ ├── Main.java → Application entry point
│ └── view.fxml → JavaFX FXML layout
```
## 📦 Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/pathfinding-visualizer.git
   cd pathfinding-visualizer
   ```


