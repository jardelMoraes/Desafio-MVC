# Desafio MVC


# Como reproduzir os projetos 


Os projetos devem ser reproduzidos pelo Visual Studio Code **(VScode)**.

O arquivo ``application-dev.properties`` foi configurado da seguinte forma:

Com essa configuração e o acréscimo da classe ``DBService`` no pacote de Service o bando de dados já é criado e configurado!

Talvez seja necessário configurar a senha do banco de dados.

```

spring.datasource.url=jdbc:mysql://localhost:3306/gft_desafio_mvc?createDatabaseIfNotExist=True
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop

### Show SQL
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.thymeleaf.cache= false

```
Com isso, o programa já funcionará e criará o banco de dados automaticamente.

````Os USUÁRIOS foram criados conforme as orientações no desafio.````

## Desafio bônus:



Como desafio Bônus foram elaboradas às fincionalidades no Front end com requisições ``AJAX`` no Back end 
