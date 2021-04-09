import os
import subprocess
import sys



if (os.path.exists("build")):
	dl=[]
	for r,ndl,fl in os.walk("build"):
		dl=[os.path.join(r,k) for k in ndl]+dl
		for f in fl:
			os.remove(os.path.join(r,f))
	for k in dl:
		os.rmdir(k)
else:
	os.mkdir("build")
cd=os.getcwd()
os.chdir("src")
if (subprocess.run(["javac","-d","../build","com/krzem/chess/Main.java"]).returncode!=0 or subprocess.run(["jar","cvmf","../manifest.mf","../build/chess.jar","-C","../build","*"]).returncode!=0):
	sys.exit(1)
os.chdir(cd)
for r,_,fl in os.walk("build"):
	for f in fl:
		if (f!="chess.jar"):
			os.remove(os.path.join(r,f))
if ("--run" in sys.argv):
	subprocess.run(["java","-jar","build/chess.jar"])
