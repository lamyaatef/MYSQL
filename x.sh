for i in ~/Downloads/SDS/build/web/WEB-INF/lib/*.jar; 
	do 
	 java -ea -jar /home/sand/Downloads/com/mindprod/jarcheck/jarcheck.jar $i  1.1 1.7
	done
