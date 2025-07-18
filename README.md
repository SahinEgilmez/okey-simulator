# Okey Simulator

A Java 17 console application that **simulates the opening deal of the classic Turkish tile‑game *Okey*** and determines which player is most likely to finish first.

---
Requirements

* **JDK 17+** – `.java-version` is set to 17.0.x
* No extra dependencies

The entry‑point is `com.segilmez.okeysimulator.Main`, already configured in the JAR manifest.

---

Implementations

* **Colour ranges**: 0‑12 (Yellow), 13‑25 (Blue), 26‑38 (Black), 39‑51 (Red).
* **Real number**: `tileId % 13 + 1` → 1 through 13 regardless of colour.
* **Okeys**: Real Okey tiles are underlined, fake Okeys take the colour/number of the real Okey.

> ✨  All colourised in ANSI so the output is easy to read in any terminal.

