# Java Arcade Game
<img width="1143" height="716" alt="Screenshot 2026-07-14 at 11 51 26 PM" src="https://github.com/user-attachments/assets/97b53cd9-1354-406c-8ed1-33e54dc55e93" />
<img width="1145" height="709" alt="Screenshot 2026-07-14 at 11 51 53 PM" src="https://github.com/user-attachments/assets/9ee1324d-01ba-4f23-b0ac-4e14b63f5dbc" />

A 2D arcade game developed in Java using Swing, featuring real-time player movement, collision detection, score tracking, animated game objects, and dynamic gameplay.

The project demonstrates object-oriented programming principles, event-driven programming, and GUI development through the implementation of an interactive fish survival game.

---

## Project Overview

This project recreates a classic arcade-style fish survival game where players control a fish that grows by consuming smaller fish while avoiding larger predators.

The game combines keyboard interaction, collision detection, object animation, timers, and sound effects to create a complete desktop gaming experience.

The project focuses on applying Java programming concepts to build an interactive graphical application from scratch.

---

## Gameplay

Players control a pink fish using the keyboard.

The objective is to eat smaller fish while avoiding dangerous gray fish before the timer expires.

Game mechanics include:

- Move using arrow keys
- Eat smaller colored fish to gain points
- Avoid larger gray fish
- Fish respawn dynamically after being eaten
- Increasing player size as the score increases
- 90-second game timer
- Final score displayed at game over

---

## Features

### Player Controls

- Keyboard movement using arrow keys
- Smooth player movement
- Boundary detection preventing movement outside the game area

---

### Collision Detection

The game continuously detects collisions between the player and surrounding fish.

Collision outcomes depend on fish size:

- Smaller fish are consumed by the player.
- Dangerous gray fish reduce the player's score.
- Collision detection is based on the distance between object centers.

---

### Dynamic Fish System

The game automatically generates and manages multiple fish.

Features include:

- Random spawning locations
- Random movement directions
- Different fish sizes
- Dangerous fish generation
- Automatic fish respawning after being eaten

---

### Game Mechanics

- Real-time score tracking
- Player growth after consuming fish
- Countdown timer
- Game over screen
- Restart functionality

---

### User Interface

The graphical interface includes:

- Start screen
- Game rules panel
- Live score display
- Countdown timer
- Game over screen
- Background images
- Background music

---

## Technical Implementation

The project was developed using object-oriented programming principles.

Core components include:

- Game loop using Java Swing Timer
- Custom game panel rendering
- Keyboard event handling
- Collision detection
- Object-oriented fish classes
- Dynamic object management
- Sound playback
- Image rendering

---

## Technologies

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- Event-driven Programming
- Java Timer
- Graphics2D
- Java Audio API

---

## Repository Structure

```text
java-arcade-game/

│
├── Code.java
└── README.md
```

---

## Repository Contents

| File | Description |
|------|-------------|
| Code.java | Complete Java source code implementing the game logic, user interface, collision detection, animation, and audio |
| README.md | Project documentation |

---

## Key Programming Concepts

This project demonstrates:

- Object-Oriented Programming
- Class Design
- Event-driven Programming
- GUI Development
- Animation
- Collision Detection
- Keyboard Input Handling
- Game Loop Design
- State Management
- Random Object Generation

---

## Future Improvements

Potential future enhancements include:

- Multiple difficulty levels
- Additional enemy types
- High score leaderboard
- Power-ups and special abilities
- Animated sprite graphics
- Sound effect enhancements
- Pause functionality
- Multiplayer mode

---

## Skills Demonstrated

- Java
- Object-Oriented Programming (OOP)
- Java Swing
- GUI Development
- Event-driven Programming
- Collision Detection
- Animation
- Graphics2D
- Game Development
- Software Design
