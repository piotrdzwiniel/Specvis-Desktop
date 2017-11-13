<h1>Specvis v1.1.1</h1>

<h3>Contents</h3>
<ul>
    <li><a href="#About">About</a></li>
    <li><a href="#Requirements">Requirements</a></li>
    <li><a href="#Download">Download</a></li>
    <li><a href="#QuickStart">Quick Start</a></li>
    <li><a href="#Resources">Resources for Learning Specvis</a></li>
    <li><a href="#CiteSpecvis">Cite Specvis</a></li>
    <li><a href="#Contribute"></a>Contribute</li>
        <ul>
            <li><a href="#CodingGuidelines">Coding Guidelines</a></li>
            <ul>
                <li><a href="#Branches">Branches</a></li>
                <li><a href="#PullRequests">Pull Requests</a></li>
                <li><a href="#CodeStyle">Code Style</a></li>
            </ul>
        </ul>
    <li><a href="#Support"></a>Support</li>
</ul>

<h3 id="About">About</h3>
<p>
    Specvis is a desktop application designed for fast and reliable perimetry-like
    <a href="https://en.wikipedia.org/wiki/Visual_field_test">visual field examination</a>.
    It is written in <a href="https://en.wikipedia.org/wiki/Java_(programming_language)">Java</a>
    to ensure considerable portability and is licensed under
    <a href="https://en.wikipedia.org/wiki/GNU_General_Public_License#Version_3">GNU GPLv3</a>.
</p>

<h3 id="Requirements">Requirements</h3>
<p>
    Specvis requires <a href="https://en.wikipedia.org/wiki/Java_virtual_machine#Execution_environment">
    Java Runtime Environment (JRE)</a></p> in version 8u121 or above. You can check whether your computer
    has an appropriate JRE version by typing <code>java -version</code> in the terminal/ command window.
    You can download the newest JRE version <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html">here</a>.
</p>

<h3 id="Download">Download</h3>
<p>
    You can download Specvis on its <a href="http://specvis.pl/index.html">website</a>. What will be
    downloaded is a *.zip archive. Unzip it in a desired location. The content of the unzipped folder
    should contain <code>Specvis.jar</code> file. You will use this file to launch Specvis application.
</p>

<h3 id="QuickStart">Quick Start</h3>
<p>
    In order to launch Specvis application double click on the <code>Specvis.jar</code> file,
    or type <code>java -jar Specvis.jar</code> in the terminal/ command window (while in the Specvis
    main directory). For further information about conducting a very first visual field examination
    please visit Specvis <a href="http://specvis.pl/quick-start.html#step-4">quick start guide</a> on its
    website.
</p>

<h3 id="Resources">Resources for Learning Specvis</h3>
<ul>
    <li><a href="http://specvis.pl/index.html">Specvis Website</a></li>
    <li><a href="http://specvis.pl/documentation.pdf">Specvis Documentation</a></li>
    <li><a href="http://journals.plos.org/plosone/article?id=10.1371/journal.pone.0186224">Article in PLoS ONE describing Specvis validation and implementation</a></li>
</ul>

<h3 id="CiteSpecvis">Cite Specvis</h3>
<p>
    If you use Specvis in your research, please cite:
    <ul>
        <li>Dzwiniel P, Gola M, Wójcik-Gryciuk A, Waleszczyk WJ (2017) Specvis: Free and open-source
        software for visual field examination. PLoS ONE 12 (10): e0186224.
        <a href="http://journals.plos.org/plosone/article?id=10.1371/journal.pone.0186224">
        https://doi.org/10.1371/journal.pone.0186224</a>.</li>
    </ul>
</p>

<h3 id="Contribute">Contribute</h3>
<p>
    I'm open to many types of contributions, from bugfixes to functionality enhancement and introduction.
    Specvis was created by one person, but is meant to be maintained by a community of academics and clinicians,
    and as such, I seek enhancements that will likely benefit a large proportion of the users who use the application.
</p>

<p>
    Before starting new code, I highly recommend to get acquainted with <code>Specvis/TODO.txt</code>
    list and opening an issue on <a href="https://github.com/piotrdzwiniel/Specvis/issues">
    Specvis GitHub</a> to discuss potential changes. Getting on the same page as the maintainers about changes
    or enhancements before to much coding is done saves everyone time and effort! Of course, I encourage
    to propose changes, that are not listed in <code>Specvis/TODO.txt</code>.
</p>

<p>
    Any contributions to Specvis, whether bug fixes, improvements or completely new functionalities,
    should be done via <i>pull requests</i> on GitHub. I assume, that you are acquainted with
    contributing to GitHub projects and you have already a GitHub account. From this point, below
    I provide step-by-step guide how to start your contribution to Specvis with the use of
    <a href="https://www.jetbrains.com/idea/download/#section=windows">IntelliJ IDEA</a> environment.
</p>

<ul>
    <li>Download, install, and launch <a href="https://www.jetbrains.com/idea/download/#section=windows">IntelliJ IDEA</a> environment.</li>

    <li>Log into your GitHub account via IntelliJ, by choosing File/Settings/Version Control/GitHub
    and filling up all text fields with appropriate information. In Host text field write <code>github.com</code>.
    In Login and Password text fields write your GitHub account credensials. Next, hit Test button,
    in order to test IntelliJ connection with your GitHub account. Finally, click Apply/OK button.</li>

    <li>Clone Specvis repository (https://github.com/piotrdzwiniel/Specvis.git) based on guidelines
    available <a href="https://www.jetbrains.com/help/idea/using-git-integration.html">here</a>.</li>

    <li>Work only on branch <code>version-X-X-X</code>. More on branches below.</li>
</ul>

<h4 id="CodingGuidelines">Coding Guidelines</h4>
<h5 id="Branches">Branches</h5>
<p>
    Specvis GitHub repository will always have two active branches: <code>master</code> and
    <code>version-X-X-X</code>. Work only on the second one.
</p>

<h5 id="PullRequests">Pull Requests</h5>
<ul>
    <li>Address one issue per pull request (PR).</li>
    <li>Avoid unnecessary cosmetic changes in PRs.</li>
    <li>Update the <code>Specvis/doc/whats_new.txt</code> file last, after PR acceptance and before merge,
    to avoid merge conflicts.</li>
</ul>

<h5 id="CodeStyle">Code Style</h5>
<ul>
    <li>I recommend to use <a href="https://www.jetbrains.com/idea/download/#section=windows">IntelliJ IDEA
    </a> environment for coding in Java.</li>

    <li>Please follow <a href="https://google.github.io/styleguide/javaguide.html">Google Java Style Guide</a>.</li>

    <li>Please write self-descriptive code with detailed comments where needed (especially describe
    with javadoc comments functionality of the implemented methods).</li>
</ul>

<h3 id="Support">Support</h3>
<p>
    If there is any technical problem with Specvis, please go to the issues section on
    <a href="https://github.com/piotrdzwiniel/Specvis/issues">Specvis GitHub</a> and try to look
    for the solution to your problem among existing topics. If you can't find the solution, than create
    a new issue and describe your problem as accurate as it is possible. If something is not working
    in Specvis (it freezes, behave oddly etc.), than try to run it from the terminal/ command window
    and than try to force this situation when something is not working. If any error will appear
    in the terminal/ command window, copy it and include it in the description of the issue. Remember,
    the more information you'll provide about the problem you've encountered using Specvis, the bigger chance
    that this problem will be solved and will not occur any more.
</p>
