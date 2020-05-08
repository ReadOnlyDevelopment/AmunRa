[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/AmunRa-Rising/AmunRa">
    <img src="https://media.forgecdn.net/avatars/232/925/637069336967519305.png">
  </a>

  <h3 align="center">Amun-Ra</h3>
</p>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Prerequisites](#prerequisites)
  * [Developing](#developing)

## About The Project

This is an addon for the Minecraft Mod GalactiCraft.
//TODO
---

## Prerequisites

- Java 1.8 JDK or OpenJDK
- Java Compatible IDE

Galacticraft Dev jars are delivered through gradle, it is not necessary to download them externally

## Developing

```
# Clone this repository
git clone https://github.com/AmunRa-Rising/AmunRa.git

# Move to the project root
cd AmunRa

# Open gradle.build file and specify Galacticraft build version
## Make any other changes while your there
Line 66: String GCBuild = "261"

# Open terminal and do command
## Or use IDE plugin that builds gradle tasks
./gradlew setupDecompWorkspace

```


