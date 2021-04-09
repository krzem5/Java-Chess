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
jfl=[]
for r,_,fl in os.walk("."):
	for f in fl:
		if (f[-5:]==".java"):
			jfl.append(os.path.join(r,f))
if (subprocess.run(["javac","-d","../build"]+jfl).returncode!=0):
	sys.exit(1)
os.chdir(cd)
cfl=[]
for r,_,fl in os.walk("build"):
	for f in fl:
		if (f[-6:]==".class"):
			cfl.append(os.path.join(r,f))
if (subprocess.run(["jar","cvmf","manifest.mf","build/chess.jar"]+cfl).returncode!=0):
	sys.exit(1)
if ("--run" in sys.argv):
	subprocess.run(["java","-jar","build/chess.jar"])
