<h1>Specvis Desktop v1.1.1</h1>
<h2>Contents</h2>
<ul>
    <li><a href="#About">About</a></li>
    <li><a href="#Requirements">Requirements</a></li>
    <li><a href="#Download">Download</a></li>
    <li><a href="#QuickStart">Quick Start</a></li>
        <ul>
            <li><a href="#Launch">Launch</a></li>
            <li><a href="#PerformingVisualFieldTest">Performing Visual Field Test</a></li>
            <li><a href="#Documentation">Documentation</a></li>
        </ul>
    <li><a href="#AcademicalValidation">Academical Validation</a></li>
    <li><a href="#CiteSpecvis">Cite Specvis</a></li>
    <li><a href="#License">License</a></li>
    <li><a href="#Support">Support</a></li>
    <li><a href="#Contribute">Contribute</a></li>
    <li><a href="#KnownIssues">Known Issues</a></li>
    <li><a href="#Contact">Contact</a></li>
</ul>

<h2 id="About">About</h2>
<p>
Specvis Desktop is a free, open-source and academically verified software for visual field examination using <a href="https://en.wikipedia.org/wiki/Visual_field_test#Static_perimetry">static perimetry</a>, which consists of displaying visual stimuli in various places on the screen in the form of points of different brightness, to which the subject responds when he sees them. As a result, we obtain a visual field sensitivity map based on the basis of which we can conclude about its condition.
</p>

<h2 id="Requirements">Requirements</h2>
<p>
In order to ensure the multiplatformity of the application (which runs on Windows, Linux, and Mac), it was written in the Java programming language. This means that the application needs Java Runtime Environment (JRE; platform for running Java applications) to be installed on your computer to run. That said you need specifally JRE in version 8.121 or above, **but not 10 and above** due to the fact, that Specvis Desktop uses JavaFX for which Oracle (a company that officially develops Java) stopped its support starting with JRE 10. Nevertheless, if you must use JRE 10 or above, you can find installing <a href="https://openjfx.io/">openjfx</a> as a solution to your possible problem with running Specvis Desktop. You just have to point to the openjfx directly and add required modules when launching Specvis Desktop from the command prompt by typing for example:

`    
java --module-path openjfx/lib --add-modules javafx.controls,javafx.fxml -jar Specvis.jar
`
   
But I strongly suggest to stick to the JRE 8.121 if possible. You can check whether your computer has an appropriate JRE version by typing <code>java -version</code> in the command prompt. You can download a specific JRE version <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html">here</a>.
</p>

<h2 id="Download">Download</h2>
<p>
You can download Specvis Desktop by clicking <a href="https://github.com/piotrdzwiniel/Specvis/raw/master/latest_build/Specvis_v1_1_1/Specvis.zip">this link</a>. What will be downloaded is a *.zip archive. Unzip it in a desired location. The content of the unzipped folder should contain <code>Specvis.jar</code> file. You will use this file to launch Specvis application.
</p>

<h2 id="QuickStart">Quick Start</h2>
<h4 id="Launch">Launch</h4>
<p>In order to launch Specvis Desktop double click on the <code>Specvis.jar</code> file or tye <code>java -jar Specvis.jar</code> in the command prompt, remembering, that you have to include Specvis directory in the command you want to execute or while being in the appropriate directory from the level of the command prompt.</p>

<h4 id="PerformingVisualFieldTest">Performing Visual Field Test</h4>
<p>First, add new or choose an existing patient in the Specvis Desktop window. Second, choose which eye you want to test. Third, select one of the predefined settins templates or choose testing settings by your own. Fourth, conduct the visual field test.</p>

<h4 id="Documentation">Documentation</h4>
<p>For more detailed information about all Specvis Desktop functionalities look into its <a href="documentation_2021_12_27.pdf">documentation</a>.</p>

<h2 id="AcademicalValidation">Academical Validation</h2>
<p>
Specvis Desktop was tested on glaucomatous, retinitis pigmentosa and stroke patients and the results were compared to results using the Medmont M700 Automated Static Perimeter, which is a commercially available, certified medical equipment used widely in different ophthalmology offices around the world. The application was also tested for inter-test intrapersonal variability. The results from both validation studies indicated low inter-test intrapersonal variability, and suitable reliability for a fast and simple assessment of visual field impairement. Specvis easily identifies visual field areas of zero sensitivity and allows for evaluation of its levels throughout the visual field. Thus, Specvis is a new, reliable application that can be successfully used for visual field examination and can fill the gap between confrontation and perimetry tests.
    
The software itself and corresponding validation studies were thoroughly described in the article <a href="https://journals.plos.org/plosone/article?id=10.1371/journal.pone.0186224">Specvis: Free and open-source software for visual field examination</a> published in PLoS ONE scientific journal.
</p>

<h2 id="CiteSpecvis">Cite Specvis</h2>
<p>
    If you use Specvis in your research that will be described in a scientific journal, please cite         Specvis Desktop by referring to the following citation:
    <ul>
        <li>Dzwiniel P, Gola M, Wójcik-Gryciuk A, Waleszczyk WJ (2017) Specvis: Free and open-source
        software for visual field examination. PLoS ONE 12 (10): e0186224.
        <a href="http://journals.plos.org/plosone/article?id=10.1371/journal.pone.0186224">
        https://doi.org/10.1371/journal.pone.0186224</a>.</li>
    </ul>
</p>

<h2 id="License">License</h2>
<p>
Specvis Desktop is currently licensed under <a href="https://en.wikipedia.org/wiki/GNU_General_Public_License#Version_3">GNU GPLv3</a> but it can change for its future, upcoming versions. However, future versions of Specvis Desktop will always include the basic functionality to reliably evaluate the field of view for free. Accesibility of the application was my main idea when creating it, so I can asure you, that it will not change in the future.
</p>

<h2 id="Support">Support</h2>
<p>
If there is any technical problem with Specvis, please go to <a href="https://github.com/piotrdzwiniel/Specvis/issues">the issues section</a> and try to look for the solution to your problem among existing topics. If you can't find the solution, than create a new issue and describe your problem as accurate as it is possible. If something is not working in Specvis (it freezes, behave oddly etc.), than try to run it from the command prompt and than try to force this situation when something is not working. If any error will appear in the command prompt, copy it and include it in the description of the issue. Remember, the more information you'll provide about the problem you've encountered using Specvis, the bigger chance that this problem will be solved and will not occur any more.
</p>

<h2 id="Contribute">Contribute</h2>
<p>
If you have an idea how Specvis Desktop can be improved, for example what new functionalities can be introduced to its future versions, please don't hesitate to contact me. Remember, that Specvis Desktop aims to be a vision diagnostic software tool.
</p>

<h2 id="KnownIssues">Known Issues</h2>

<ul>
    <li><b>Problem:</b> Specvis Desktop is not running when double clicked on</b> <code>Specvis.jar</code></li>
    <ul>
    <li><i>Explanation:</i> The most plausible explanation to this problem is that you try to run Specvis Desktop from the location where you need to have administrative permissions. For example, if you downloaded Specvis Desktop on your main drive/partition (most likely "C") which is also a drive with an operating system installed on it and drive/partition is protected with administrative permissions then running Specvis Desktop by simply double clicking on it can be impossible.</li>
    <li><i>Solution:</i> If you want to keep Specvis Desktop on a restricted drive/partition, then you can run the application via command prompt. However, commant prompt should be launched with the use of "run as the administrator" mode. Other, simpler solution to the problem, is to just place Specvis Desktop folder in a location, where any administrative permissions are not required for the user.</li>
    </ul>
</ul>

<ul>
    <li><b>Problem:</b> Issues with launching/running Specvis Desktop using JRE in higher versions then 8u121</li>
    <ul>
        <li><i>Explanation:</i> You can face some problems while trying to launch/run Specvis Desktop using JRE in version 11 or higher. It's due to the fact that Specvis Desktop uses JavaFX library for which Oracle (official developer of the Java programming language) made it open sourced from JRE 11 and later releases, thus stopped official support of it with their runtime environments.</li>
        <li><i>Solution:</i> If you want to use newer version of JRE you can supplement lack of the official JavaFX library support by installing <a href="https://openjfx.io/">openjfx</a> library. When installation is completed you just have to point to an appropriate jfx modules while running Specvis Desktop (using command prompt is required) <code>java --module-path path1/openjfx/lib --add-modules javafx.controls,javafx.fxml -jar path2/Specvis.jar</code>. Remember, that <code>path1</code> and <code>path2</code> has to point to direct paths to openjfx folder and Specvis Desktop folder, respectively. However, if possible, it is recommended to use specifically JRE in version 8u121.</li>
    </ul>
</ul>

<ul>
    <li><b>Problem:</b> Despite changing the settings, Specvis Desktop behaves as if the settings have not been changed.</li>
    <ul>
        <li><i>Explanation:</i> Specvis Desktop uses few input fields for numerical values where you can type arbitraty input by yourself. However, Specvis Desktop will not save automatically provided value unless you will "accept" provided value.</li>
        <li><i>Solution:</i> When changing settings with the use of input fields make sure that you hit ENTER after typing a desired value in the field.</li>
    </ul>
</ul>

<ul>
    <li><b>Problem:</b> Visual field examination procedure is interrupted by Specvis Desktop crash/error.</li>
    <ul>
        <li><i>Explanation:</i> There are situations when Specvis Desktop visual field examination procedure can be interrupted or it can crash. The result of such crash is: 1) freeze of the procedure or 2) visual field graphical map indicating that no responses were provided by the patient during the test.</li>
        <li><i>Solution:</i> Make sure, that all settings numerical inputs you provide are confirmed by hitting ENTER key on your keyboard. If problem persists, do not use any of the available fixation (gaze) monitor techniques.</li>
    </ul>
</ul>

<ul>
    <li><b>Problem:</b> Specvis Desktop freezes during the attempt to redraw visual field graphical map.</li>
    <ul>
        <li><i>Explanation:</i> Specvis Desktop uses pretty complicated computation to create visual field graphical map based on the results from the test. Thus, it takes time, to redraw the map after changing isofactor and interpolation values, whereas time it takes depends on the hardware configuration you are using (computational power). The lower the isofactor value and the higher the interpolation value, the longer time needed for the computations standing behind redraw function.</li>
        <li><i>Solution:</i> Wait. Specvis Desktop thinks. You can also use higher isofactor and lower interpolation values in order to decrease time needed for redrawing the visual field graphical map.</li>
    </ul>
</ul>

<h2 id="Contact">Contact</h2>
<p>For any questions write at <b>piotr.dzwiniel@gmail.com</b>.</p>
