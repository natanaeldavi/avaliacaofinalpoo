# 🎟️ Sistema de Controle de Eventos e Venda de Ingressos

Aplicação desktop desenvolvida em **Java** utilizando **Swing** para a interface gráfica, projetada com foco em **Programação Orientada a Objetos (POO)**, boas práticas de encapsulamento, tratamento de exceções customizadas e arquitetura modularizada.

> **Contexto Acadêmico:** Projeto desenvolvido para a disciplina de Programação Orientada a Objetos (Fatec Mogi Mirim — Prof. Maromo), abordando o **Tema 08: Controle de Eventos e Venda de Ingressos**.

---

## 🚀 Funcionalidades Principais

* **Emissão e Venda de Ingressos:** Cadastro de bilhetes nas modalidades **Inteira** e **Meia-Entrada** (com registro de documento estudantil), gerando automaticamente o status inicial (`DISPONIVEL`) e o carimbo de data/hora da compra (`LocalDateTime`).
* **Sistema de Cupons Promocionais:** Aplicação de descontos dinâmicos através do contrato da interface `Promocional`, validando códigos em tempo real via `Map` e respeitando regras polimórficas por tipo de ingresso.
* **Controle de Portaria (Check-in):** Validação de entrada que altera o ciclo de vida do bilhete para `UTILIZADO` e registra o horário exato da baixa, bloqueando tentativas de reentrada ou uso de bilhetes cancelados.
* **Cancelamento de Bilhetes:** Invalidação controlada de ingressos ainda disponíveis com auditoria de horário.
* **Ordenação e Relatórios Gerenciais:** Listagem ordenada sequencialmente por código (`Comparable`) e geração de balanço financeiro e de público em tempo real.

---

## 🏗️ Arquitetura e Estrutura de Pacotes

O projeto segue uma divisão clara de responsabilidades inspirada no padrão **MVC (Model-View-Controller)**:

```text
controle_ingressos/
└── src/
    ├── app/
    │   └── Principal.java                 # Ponto de entrada da aplicação (método main)
    ├── controller/
    │   └── GerenciadorIngressos.java      # Regras de negócio, persistência em memória (List) e relatórios
    ├── exception/
    │   └── IngressoInvalidoException.java # Checked Exception personalizada para regras de domínio
    ├── interfaces/
    │   └── Promocional.java               # Contrato de descontos com validação estática (Map.of)
    ├── model/
    │   ├── Ingresso.java                  # Classe abstrata base (Comparable, Promocional, equals/hashCode)
    │   ├── IngressoInteira.java           # Especialização para ingresso padrão
    │   ├── IngressoMeia.java              # Especialização para meia-entrada com carteirinha
    │   └── StatusIngresso.java            # Enumeração do ciclo de vida (DISPONIVEL, UTILIZADO, CANCELADO)
    └── view/
        └── TelaControle.java              # Interface gráfica desktop desenvolvida em Java Swing
```

---

## 🧠 Conceitos de POO Aplicados

* **Abstração e Herança:** A superclasse abstrata `Ingresso` concentra atributos comuns, controle de estado e histórico temporal (`LocalDateTime`), sendo estendida por `IngressoInteira` e `IngressoMeia`.
* **Polimorfismo:**
  * *Sobrescrita de cálculo:* Cada subclasse implementa sua própria regra em `calcularValorFinal()`.
  * *Contrato por Interface:* A interface `Promocional` define o comportamento de aplicação de cupons (`aplicaCupom`) e utiliza métodos `static` do Java moderno para consulta de cupons válidos.
* **Ordenação e Identidade de Objetos:** Implementação da interface nativa `Comparable<Ingresso>` (ordenando pelo código sequencial via `Collections.sort`) acompanhada da sobrescrita coerente de `equals()` e `hashCode()`.
* **Tratamento de Exceções:** Uso da *Checked Exception* `IngressoInvalidoException` para interceptar violações de regra de negócio (como validar um ingresso já utilizado, cancelar um bilhete baixado ou aplicar cupons inválidos) e exibi-las de forma amigável via `JOptionPane`.
* **Enums com Comportamento:** `StatusIngresso` encapsula os estados possíveis do bilhete juntamente com suas respectivas descrições textuais.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 17+)
* **Interface Gráfica:** Java Swing (`javax.swing`)
* **API de Data e Hora:** `java.time.LocalDateTime`
* **Coleções:** `List`, `ArrayList`, `Map`, `Collections`
* **IDE Utilizada:** IntelliJ IDEA

---

## ▶️ Como Executar o Projeto

1. Clone este repositório para a sua máquina local:
   ```bash
   git clone https://github.com/seu-usuario/controle-ingressos.git
   ```
2. Abra a pasta do projeto na sua IDE Java de preferência (IntelliJ IDEA, Eclipse ou VS Code).
3. Certifique-se de que o **JDK 17** (ou superior) está configurado nas propriedades do projeto.
4. Localize o arquivo `src/app/Principal.java` e execute o método `main`.

---

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais e de portfólio. Sinta-se à vontade para explorar o código e utilizá-lo como referência de estudos em Orientação a Objetos.