# amazon-rekognition-poc

#### **Technologies and Frameworks:**
* JDK 11
* Spring Boot
* Amazon Rekognition

#### **Requirements:**
* Create an AWS account: https://aws.amazon.com/pt/
* Configure a IAM User: https://docs.aws.amazon.com/rekognition/latest/dg/setup-awscli-sdk.html
* Fill the "application.yml" file with IAM accessKey and secretKey data

### How to run
#### Compare Faces
* URL http://localhost:8080/compareFaces
    - parameters:
        * imageSource
        * imageTarget
    
#### Photo of a photo verification
* URL http://localhost:8080/checkPhoto
    - parameters:
      * image
