This is a project that add BFT functionality into hadoop MR. 

We have several hadoop MR runtimes running in several clusters, and in case of one tasks do not have equal result, a new task is launched in a new cluster.

The communication of the clusters, and the job management is made by a python component called hadoop-mapreduce-web-python. This component will be integrated later with the component hadoop-mapreduce-manager-python-2.X (maybe 1).

For more info about hadoop-mapreduce-web-python, check the README of that project.
