### 1. Renomear as pastas
Antes de executar os testes, renomear as pastas para "api-test


### 1. Executar os Testes
Este comando limpa execuções anteriores e roda a suíte de testes atual:
```bash
mvn clean test

### 2. Gerar o Report
Este comando processa os arquivos JSON gerados na pasta target/allure-results e abre o relatório no seu navegador:
```bash
mvn allure:report

### 3. Visualizar o Relatório no Navegador
Este comando abre o relatório no seu navegador
```bash
mvn allure:serve