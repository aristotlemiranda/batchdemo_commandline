# batchdemo_commandline

#To run application via commandline with arguments 
#Refer to JobAppRunner class for implementation on how to read parameters via ApplicationRunner'
#TODO parameter verification


java -jar target/batch_demo-0.0.1-SNAPSHOT.jar  --jobname=RTGS-SGD-FULL-SOD --jobName=sample --skipDependency=true --job-worflow=step1,step2,step3
