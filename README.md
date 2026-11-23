# Sistema de Gestão de Cinema e Controle de Ingressos

Aplicação desktop desenvolvida em **Java** com interface gráfica em **Java Swing**, estruturada sob o padrão arquitetural **MVC (Model-View-Controller)** e princípios de Programação Orientada a Objetos (herança, polimorfismo, encapsulamento, interfaces e tratamento de exceções customizadas).

## Funcionalidades

* **Gestão de Filmes:** Cadastro de títulos em cartaz com classificação etária, sinopse, nota média, preço base do ingresso e acompanhamento de faturamento individual por filme.
* **Gestão de Sessões:** Agendamento de sessões vinculadas a filmes cadastrados, com definição de sala, data/hora e controle automático de lotação e vagas disponíveis.
* **Bilheteria (Emissão de Ingressos):**
  * Geração de códigos únicos auto-incrementais.
  * Associação bidirecional entre `Ingresso` e `SessaoCinema`, bloqueando vendas quando a sessão atinge a lotação máxima.
  * Suporte a **Ingresso Inteira** (valor integral com possibilidade de aplicação de cupom promocional não cumulativo, permitindo substituição apenas por descontos maiores) e **Meia-Entrada** (50% de desconto mediante número de carteirinha estudantil, sem acúmulo com cupons).
* **Portaria e Cancelamentos:**
  * Validação de entrada na portaria (`DISPONIVEL` $\rightarrow$ `UTILIZADO`) com registro de data e hora de baixa.
  * Cancelamento de bilhetes disponíveis (`DISPONIVEL` $\rightarrow$ `CANCELADO`), devolvendo automaticamente a vaga para a sessão correspondente.
* **Relatórios Financeiros em Tempo Real:** Cálculo dinâmico de receita total, receita por filme e receita por sessão encerrada utilizando **Java Streams**.

## Estrutura do Projeto

```text
src/
├── controller/
│   └── GerenciadorIngressos.java    # Regras de negócio, buscas e cálculos via Streams
├── exception/
│   ├── CupomInvalidoException.java
│   ├── FilmeInvalidoException.java
│   ├── IngressoInvalidoException.java
│   └── SessaoInvalidaException.java
├── interfaces/
│   └── Promocional.java             # Contrato e tabela de cupons válidos
├── model/
│   ├── Filme.java
│   ├── Ingresso.java                # Classe abstrata base
│   ├── IngressoInteira.java
│   ├── IngressoMeia.java
│   ├── SessaoCinema.java
│   └── StatusIngresso.java          # Enum (DISPONIVEL, UTILIZADO, CANCELADO)
├── view/
│   └── TelaControle.java            # Interface gráfica Swing em abas (JTabbedPane)
└── Main.java                        # Ponto de entrada da aplicação
```

## Como Executar

1. Certifique-se de ter o **JDK 17** (ou superior) instalado.
2. Compile e execute a classe `Main.java` pela sua IDE (IntelliJ IDEA, Eclipse ou VS Code) ou via terminal:
   ```bash
   javac -d out src/**/*.java src/*.java
   java -cp out Main
   ```

## Transparência no Uso de IA Generativa

Em conformidade com as diretrizes acadêmicas do projeto, o desenvolvimento seguiu uma divisão clara entre autoria manual e assistência por Inteligência Artificial Generativa (Google Gemini):

* **Desenvolvido manualmente (Autoria própria):**
  * Toda a arquitetura de domínio e modelagem Orientada a Objetos (`model`, `interfaces` e `exception`), incluindo a relação bidirecional entre `SessaoCinema` e `Ingresso`, controle de lotação e devolução de vagas.
  * Toda a lógica de controle, filtros e agregações financeiras com Java Streams no `GerenciadorIngressos`.
  * Implementação completa dos métodos de eventos, validações de formulário, tratamento de exceções na tela e sincronização de dados (`atualizarTela`) na `TelaControle`.
* **Gerado com apoio de IA Generativa:**
  * Geração de protótipo visual (mockup) inicial para concepção do layout da interface desktop.
  * Estruturação visual base da classe `TelaControle` em Java Swing (organização do `JTabbedPane`, layouts `GridBagLayout` e `BorderLayout`, configuração estética das `JTable`, renderizadores customizados de status e estilização dos botões).