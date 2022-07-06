# smart-secure-home
Smart home with security

## Features and technologies

- Common features
  - HTTPS (SSL/TLS)
  - Web sockets for alarms
  - RabbitMQ for device communication with smart-home application
  - MongoDB
  - PostgreSQL
- Admin app
  - Spring backend
  - Angular frontend
  - CSR creation
  - Certificate creation
  - User managment
  - Real estate configuration
  - Devices configuration for each object in real estate
  - Alarms configuration
  - Real time alarm notifications (web sockets)
  - Logs
- Smart-home app
  - Spring backend
  - Angular frontend
  - Real-time alarm notifications (web sockets)
  - Object messages from devices
  - Alarms report on demand for a given time period
- Device app
  - Spring backend
  - Simulates device signals
- Rules kjar
  - Project with Drools rules for admin app alarms
  - Templates and Complex event processing (CEP)
- Device-Rules kjar
  - Project with Drools rules for device alarms
  - Templates and Complex event processing (CEP)
  
## Penetration testing

Application was tested with OWASP top ten security issues in mind.

- Software
  - OWASP ZAP
  - Burp pro

## Admin application screenshots

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177536935-61380e4f-d249-4eef-b185-110780f8053a.png" />
  <p align="center">Ilustration 1 - CSR Request Form.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177537183-2a325b0b-d155-41d9-ba29-e474c28d77bc.png" />
  <p align="center">Ilustration 2 - Certificates.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177537591-95ba88ed-a11a-44fe-80f3-d6a3f6888408.png" />
  <p align="center">Ilustration 3 - Users.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177537735-025edaee-8d79-4a9a-a526-4f6cf589d0d7.png" />
  <p align="center">Ilustration 4 - Real estate managment.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177537869-9c2041cc-a918-4648-b5ab-94f91a4459ba.png" />
  <p align="center">Ilustration 5 - Real estate creation.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177538101-90f90518-e0d1-40cb-81f9-e1211b0e7923.png" />
  <p align="center">Ilustration 6 - Real estates.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177538004-1c8f5e3c-1054-41e2-ae36-b26a19e63bb5.png" />
  <p align="center">Ilustration 7 - Real estate device configuration.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177538239-52653e12-ac6e-4251-8b54-b82b374aecd6.png" />
  <p align="center">Ilustration 8 - Server logs.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177538330-5d2b8fd5-00f4-4aad-8471-fc15be06b61e.png" />
  <p align="center">Ilustration 9 - Server alarms.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177538556-f9a0992c-9514-43de-89de-cebce7867c2a.png" />
  <p align="center">Ilustration 10 - Server alarm rules.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177538430-29afd810-1246-48a7-8c2a-63833e6e5762.png" />
  <p align="center">Ilustration 11 - Server device alarm rules.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177538665-7415b788-bc26-41b9-b3f5-00b6b82e8956.png" />
  <p align="center">Ilustration 12 - User registration.</p>
</div>

## Smart-home application screenshots

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177539092-0c10bc59-bea2-4a58-8698-7e4f2d7cde09.png" />
  <p align="center">Ilustration 13 - User's smart-home objects with alarm notifications in real time.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177539332-cf221079-b782-49a0-a33d-bc8b67d6d95b.png" />
  <p align="center">Ilustration 14 - Smart-home objects messages that were recieved from the devices.</p>
</div>

<div align="center">
<img alt="signal-visualization" align="center" width="100%" src="https://user-images.githubusercontent.com/34657562/177539500-aee7a38e-bf8e-4e34-934e-7d3d8dffe3e4.png" />
  <p align="center">Ilustration 15 - Smart-home objects alarms report.</p>
</div>


