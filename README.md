<!-- http://piotrdzwiniel.github.io/Specvis-Desktop/ -->

## Table of Contents

1. [About](#about)
2. [System requirements](#system-requirements)
3. [How to download](#how-to-download)
4. [Quick start](#quick-start)
    - [Launch it](#launch-it)
    - [Perform a visual field test](#perform-a-visual-field-test)
    - [Read the results](#read-the-results)
    - [Documentation](#documentation)
5. [Scientific validation](#scientific-validation)
6. [Cite Specvis](#cite-specvis)
7. [License](#license)
8. [Support](#support)
9. [Contribute](#contribute)
10. [Known issues](#known-issues)
11. [Upcoming changes](#upcoming-changes)
12. [Donate](#donate)
13. [Contact us](#contact)

## About

Specvis Desktop is a free, open-source, and [scientifically verified](#scientific-validation)
software for visual field examination
using [static perimetry](https://en.wikipedia.org/wiki/Visual_field_test#Static_perimetry),
which involves displaying visual stimuli in various locations on the screen as points of
different brightness, to which the subject responds when they see them. This results in a
visual field sensitivity map, which can provide insights into its condition. **Specvis Desktop
has very low hardware requirements and can be launched on any modern laptop computer.**

![Figure](figure_plosone.jpg)

**Figure.** Visual field graphical maps
from [retinitis pigmentosa](https://en.wikipedia.org/wiki/Retinitis_pigmentosa) patient. **A.**
The results from Medmont M700 professional perimeter. **B.** The results from Specvis Desktop
application.

## System requirements

In order to ensure the multiplatform nature of the application (which runs on Windows, Linux,
and Mac), it was written in the Java programming language. This means that the application
requires Java Runtime Environment (JRE), which is a platform for running Java applications, to
be installed on your computer. Specifically, Specvis Desktop requires JRE version 8.121 or
above, but not versions 10 and above. This is because Specvis Desktop uses JavaFX, for which
Oracle (the company that officially develops Java) stopped its support starting with JRE 10.

However, if you must use JRE 10 or above, you can install [openjfx](https://openjfx.io/) to
resolve any potential issues with running Specvis Desktop. You just need to point to the
openjfx directory and add the required modules when launching Specvis Desktop from the command
prompt, for example:

`    
java --module-path openjfx/lib --add-modules javafx.controls,javafx.fxml -jar Specvis.jar
`

Nevertheless, I strongly recommend sticking to JRE 8.121 if possible. You can check whether
your computer has an appropriate JRE version by typing `java -version` in the command prompt.
You can download a specific JRE
version [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

## How to download

You can download Specvis Desktop as
a [*.zip](https://github.com/piotrdzwiniel/Specvis/raw/master/latest_build/Specvis_v1_1_1/Specvis.zip)
or [*.tar.gz](https://github.com/piotrdzwiniel/Specvis/raw/master/latest_build/Specvis_v1_1_1/Specvis.tar.gz).
After downloading, unpack the archive in your desired location. The unpacked archive should
contain the `Specvis.jar` file, which you will use to launch the Specvis application.

## Quick start

### Launch it

To launch Specvis Desktop, double-click on the `Specvis.jar` file or
type `java -jar Specvis.jar` in the command prompt. Remember to include the Specvis directory
in the command you want to execute, or navigate to the appropriate directory from the command
prompt.

### Perform a visual field test

First, add a new patient or select an existing one in the Specvis Desktop window. Second,
choose which eye you want to test. Third, select one of the predefined settings templates or
customize the testing settings according to your preferences. Fourth, conduct the visual field
test.

### Read the results

At the moment, Specvis gives you two ways to work with the results. First, you can preview them
in the program. Second, you can export them and work with them using other tools.

If you choose the latter approach, you will get two files when exporting the results. The first
is called `session_info` and contains meta data about the study. The second is
called `session_data` and contains the results of the study.

If you export both files to TXT format,
then [here](https://github.com/piotrdzwiniel/Specvis-Desktop/tree/master/python_scripts) you
will find two scripts written in
Python that allow you to convert TXT files to CSV format (`TxtToCsv.py`) and visualize the
results converted to CSV format in the form of contour maps (`DisplayResults.py`).

In addition, below is a description of the `session_info` and `session_data` file columns.

```python
"""
Columns of session_info file:
- Parameter (str): Specvis procedure parameter name.
- Value (str): Parameter's value.

Columns of session_data file:
- Index (int): Index/number of the stimuli location.
- PosPxX (int): The position of the visual stimulus presented on the screen on the X-axis, expressed in pixels.
- PosPxY (int): The position of the visual stimulus presented on the screen on the Y-axis, expressed in pixels.
- DistDegFromFixPointX (float): The distance of the visual stimulus from the fixation point on the X-axis, in degrees.
- DistDegFromFixPointY (float): The distance of the visual stimulus from the fixation point on the Y-axis, in degrees.
- ThresholdBrightness (float): Recorded brightness threshold of the visual stimulus.
- ThresholdLuminance (float): Recorded luminance threshold of the visual stimulus.
- ThresholdDecibel (float): Recorded decibel threshold of the visual stimulus.

Indexing stimuli rule:
03 01 | 05 07
02 00 | 04 06
-------------
14 12 | 08 10
15 13 | 09 11

Position of stimuli:
- The position of the visual stimulus is expressed in pixels and is counted from left to right (X axis) and top to bottom (Y axis).

Distance from fixation point:
- For the X axis, the values to the left of the fixation point take on negative values, and to the right take on positive values.
- For the Y-axis, values above the fixation point take on negative values, and values below take on positive values.
- Values are expressed in degrees.
"""
```

### Documentation

For more detailed information about all Specvis Desktop functionalities, refer to
its [documentation](documentation_2022_01_02.pdf).

## Scientific validation

Specvis Desktop was tested on glaucomatous, retinitis pigmentosa, and stroke patients, and the
results were compared to those obtained using the Medmont M700 Automated Static Perimeter. The
Medmont M700 is a commercially available, certified medical equipment widely used in different
ophthalmology offices around the world. The application was also tested for inter-test
intrapersonal variability. The results from both validation studies indicated low inter-test
intrapersonal variability and suitable reliability for a fast and simple assessment of visual
field impairment. Specvis easily identifies visual field areas of zero sensitivity and allows
for evaluation of its levels throughout the visual field. Thus, Specvis is a new, reliable
application that can be successfully used for visual field examination and can fill the gap
between confrontation and perimetry tests.

The software itself and corresponding validation studies were thoroughly described in the
article mentioned below (Dzwiniel et al., 2017).

## Cite Specvis

If you use Specvis in your research that will be described in a scientific journal, please cite
Specvis Desktop by referring to the following publication:

- Dzwiniel, P., Gola, M., Wójcik-Gryciuk, A., & Waleszczyk, W. J. (2017). Specvis: Free
  and open-source software for visual field examination. PloS one, 12(10),
  e0186224. https://doi.org/10.1371/journal.pone.0186224

## License

Specvis Desktop is currently licensed
under [GNU GPLv3](https://en.wikipedia.org/wiki/GNU_General_Public_License#Version_3), but the
license may change for future versions. However, future versions of Specvis Desktop will always
include the basic functionality to reliably evaluate the field of view for free. Accessibility
of the application was my main priority when creating it, so I can assure you that this
principle will not change in the future.

## Support

If you encounter any technical issues with Specvis, please
visit [the issues section](https://github.com/piotrdzwiniel/Specvis/issues) and attempt to
locate a solution among existing topics. If you cannot find a solution, please create a new
issue and describe your problem as accurately as possible. If Specvis is not functioning
properly (freezing, behaving oddly, etc.), try running it from the command prompt and then
attempt to reproduce the issue. If any error messages appear in the command prompt, copy them
and include them in the description of the issue. Remember, the more information you provide
about the problem you've encountered with Specvis, the greater the chance that the issue will
be resolved and will not reoccur.

## Contribute

If you have an idea on how Specvis Desktop can be improved, such as new functionalities to
introduce in its future versions, please don't hesitate to contact me. Remember, Specvis
Desktop aims to be a vision diagnostic software tool.

## Known issues

- **Problem:** Specvis Desktop does not run when double-clicked on `Specvis.jar`.
    - *Explanation:* The most likely explanation for this problem is that you are trying to run
      Specvis Desktop from a location where administrative permissions are required. For
      example, if you downloaded Specvis Desktop on your main drive/partition (most likely "
      C"), which also contains your operating system and is protected by administrative
      permissions, running Specvis Desktop by simply double-clicking on it may be impossible.
    - *Solution:* If you want to keep Specvis Desktop on a restricted drive/partition, you can
      run the application via the command prompt. However, the command prompt should be
      launched with "run as administrator" mode. Alternatively, a simpler solution to the
      problem is to place the Specvis Desktop folder in a location where no administrative
      permissions are required for the user.

- **Problem:** Issues with launching/running Specvis Desktop using JRE versions higher than
  8u121.
    - *Explanation:* You may encounter problems when attempting to launch/run Specvis Desktop
      using JRE versions 11 or higher. This is because Specvis Desktop utilizes the JavaFX
      library, which Oracle (the official developer of the Java programming language) made open
      source from JRE 11 onwards, thereby discontinuing official support for it in their
      runtime environments.
    - *Solution:* If you prefer to use a newer version of JRE, you can supplement the lack of
      official JavaFX library support by installing the [openjfx](https://openjfx.io/) library.
      After installation, you need to specify the appropriate jfx modules when running Specvis
      Desktop via the command prompt. For
      example: `java --module-path path1/openjfx/lib --add-modules javafx.controls,javafx.fxml -jar path2/Specvis.jar`.
      Ensure that `path1` and `path2` point to the direct paths to the openjfx folder and the
      Specvis Desktop folder, respectively. However, if possible, it is recommended to use
      specifically JRE version 8u121.

- **Problem:** Despite changing the settings, Specvis Desktop behaves as if the settings have
  not been changed.
    - *Explanation:* Specvis Desktop provides input fields for numerical values, allowing users
      to input arbitrary values manually. However, Specvis Desktop will not automatically save
      the provided value unless it is "accepted."
    - *Solution:* When changing settings using input fields, ensure that you press ENTER after
      typing the desired value in the field.

- **Problem:** Visual field examination procedure is interrupted by Specvis Desktop
  crash/error.
    - *Explanation:* There are situations where the visual field examination procedure in
      Specvis Desktop can be interrupted or crash. This results in either freezing of the
      procedure or a visual field graphical map indicating that no responses were provided by
      the patient during the test.
    - *Solution:* Ensure that all numerical inputs for settings are confirmed by pressing the
      ENTER key on your keyboard. If the problem persists, refrain from using any available
      fixation (gaze) monitor techniques.

- **Problem:** Specvis Desktop freezes during the attempt to redraw the visual field graphical
  map.
    - *Explanation:* Specvis Desktop utilizes complex computations to create a visual field
      graphical map based on test results. Therefore, it takes time to redraw the map after
      changing isofactor and interpolation values, with the time required depending on your
      hardware configuration (computational power). Lower isofactor values and higher
      interpolation values result in longer computation times for redrawing the visual field
      graphical map.
    - *Solution:* Wait patiently for Specvis Desktop to complete the computation.
      Alternatively, consider using higher isofactor and lower interpolation values to decrease
      the time needed for redrawing the visual field graphical map.

## Donate

Specvis Desktop is a free and open-source application designed for visual field examination.
Developed by a single individual pro publico bono (for the public good), its primary aim is to
serve regions of the world where access to professional ophthalmic healthcare is limited. With
a commitment to maintaining free access to the application for as long as possible and
continuing its development for future versions, your support through a donation towards Specvis
Desktop would be greatly appreciated.

Donations are processed and secured by [Stripe](https://stripe.com/), ensuring their complete
safety. You can also make repeat donations as frequently as you deem appropriate.

- [€5.00 EUR](https://buy.stripe.com/dR616276d5GBa7m288)
- [€10.00 EUR](https://buy.stripe.com/00g5micqx3yt3IY9AB)
- [€25.00 EUR](https://buy.stripe.com/dR67uq9el1ql7Ze28a)
- [€50.00 EUR](https://buy.stripe.com/8wM7uqbmt4Cx7Ze7sv)
- [€100.00 EUR](https://buy.stripe.com/7sIbKGduBfhb2EU9AE)

## Contact us

In any matter, please write to **specvis.desktop@gmail.com**.

