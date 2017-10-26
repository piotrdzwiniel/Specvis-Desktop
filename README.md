<h1>Specvis v1.1.1</h1>
<h3>Definition and usage of the application</h3>
Specvis is a desktop application designed for visual field examination in humans written in the Java programming language ensuring unrestricted portability on any personal computer or laptop. It has an intuitive and easy to use graphical user interface and does not require special computer or programming skills. We have made Specvis freely available as an open-source application based on GNU GPLv3 license. It can be downloaded and used without charge, and allows unrestricted modification. Similar to other Automated Static Perimeters, Specvis displays a single, specific, light stimulus at different locations on the computer screen, in order to assess a luminance threshold across the visual field. A comprehensive description of the application with its scientific and clinical validation can be found in <a href="http://journals.plos.org/plosone/article?id=10.1371%2Fjournal.pone.0186224">PLoS ONE article</a>. A complementary materials can be also found in its <a href="http://www.specvis.pl/documentation-for-ver-1-1-0.pdf">documentation</a>.

<h3>Source code, requirements and availability</h3>
Specvis was written in the Java programming language and requires installation of the Java Runtime Environment (JRE) in version 8u121 or above. You can check the JRE version by typing in the terminal or command window `java -version`. The latest version of JRE can be downloaded from <a href="http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html">here</a>.

After ensuring that the appropriate version of the JRE is installed, the current version of Specvis can be found in the <a href="https://github.com/piotrdzwiniel/Specvis/tree/master/latest_build">latest_build</a> folder. You can find there two main directories, i.e. `Specvis IntelliJ Project` and `Specvis Executable Pack`.

The first one contains complete source code of the latest Specvis release which can be imported to the IntelliJ IDEA. The second one contains Specvis application with its related files.

From the other hand, if you are interested in older versions of Specvis and their source code, you can find them in the <a href="https://github.com/piotrdzwiniel/Specvis/tree/master/old_builds">old_builds</a> folder.

Everything "around" the `latest_build` and the `old_builds` folders is a working ground for a new future realease of Specvis. So don't be surprised if content of e.g. the <a href="https://github.com/piotrdzwiniel/Specvis/tree/master/src">src</a> folder will change few times a week etc.

<h3>Running the application</h3>

After you downloaded and unpacked the `Specvis Executable Pack/Specvis.zip` file (content of the zip file in shown in the Figure 1), you can just double click on the `Specvis.jar`. You can also run Specvis from the terminal by typing `java -jar Specvis.jar`.

&nbsp;

<img src="https://github.com/piotrdzwiniel/Specvis/raw/master/readme_content/img/directory.png" width="50%">
<b>Figure 1.</b> Content of the downloaded Specvis zip archive.

&nbsp;

<h3>Support</h3>

If you have a problem with the application, please first make sure that you are familiar with <a href="http://www.specvis.pl/index.html">content of the Specvis homepage</a>, and if you still need help, please go to the <a href="https://github.com/piotrdzwiniel/Specvis/issues">Issues</a> section. While you are there try to look for the solution to your problem in existing issues. If you can't find solution, than create a new issue keeping in mind - the more accurately the problem is described the greater is chance for fast answer to it.

<h3>License</h3>

The source code and the application itself are released under the terms of <a href="https://github.com/piotrdzwiniel/Specvis/blob/master/GNU%20GPL%20v3.txt">GNU General Public License in version 3</a> as published by the Free Software Foundation. Software covered by this license is and will be free and open-source. In general, this license ensures, that everyone can use the software and modify it, however, each new release is also covered by the same license, so the freedom and capacity of the software remains preserved. This policy refers to the software as whole but also to each individual source code file.

<h3>Citing Specvis</h3>

If you use Specvis in your research, please cite:

Dzwiniel P, Gola M, Wójcik-Gryciuk A, Waleszczyk WJ (2017) Specvis: Free and open-source software for visual field examination. PLoS ONE 12 (10): e0186224. <a href="https://doi.org/10.1371/journal.pone.0186224">https://doi.org/10.1371/journal.pone.0186224</a>.

<h3>Example screen-shots</h3>

<img src="https://github.com/piotrdzwiniel/Specvis/raw/master/readme_content/img/1.png" width="60%">
<b>Figure 2.</b> Specvis starting window. Here you can manage patient data as well as choose previously saved settings, so there is no need to adjust everything at each diagnostic session.

&nbsp;

<img src="https://github.com/piotrdzwiniel/Specvis/raw/master/readme_content/img/2.png" width="60%">
<b>Figure 3.</b> You can easily preview patient's visual field results for example in a form of a visual field graphical map. Here, we present example results for stroke patient with homonymous right hemianopia.

&nbsp;

<img src="https://github.com/piotrdzwiniel/Specvis/raw/master/readme_content/img/3.png" width="60%">
<b>Figure 4.</b> Specvis offers at the moment three different fixation monitor techniques. One of them called "Both" incorporates testing assumed blind spot location as well as displaying test stimulus in a fixation point location.

&nbsp;

<img src="https://github.com/piotrdzwiniel/Specvis/raw/master/readme_content/img/4.png" width="60%">
<b>Figure 5.</b> If you use Specvis on computer with two screens you can supervise conductance of the test procedure in a dedicated window.

&nbsp;

<img src="https://github.com/piotrdzwiniel/Specvis/raw/master/readme_content/img/5.png" width="60%">
<b>Figure 6.</b> From Specvis in version 1.1.0 you can also compare two datasets for the same patient, so it is possible to monitor visual field condition between subsequent tests.
