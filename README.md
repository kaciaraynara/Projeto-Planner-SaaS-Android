# Planner SaaS

## Informações do Aluno

Nome: RAYNARA KÁCIA MAGALHÃES FONTELES

Data de Entrega: 04/06/2026

---

# Tema do Projeto

Aplicativo Android para organização e gerenciamento de projetos voltado para desenvolvedores.

---

# Objetivo do Projeto

O objetivo deste projeto foi desenvolver uma aplicação Android voltada para organização e gerenciamento de projetos, auxiliando desenvolvedores no acompanhamento das suas atividades, tarefas e demandas durante o processo de desenvolvimento de software.

A proposta principal do aplicativo é oferecer uma ferramenta prática para melhorar a organização, aumentar a produtividade e facilitar o gerenciamento das etapas de um projeto, reunindo informações importantes em um único ambiente.

Além disso, o projeto teve como objetivo aplicar conceitos estudados durante a disciplina, utilizando arquitetura moderna, persistência local de dados e desenvolvimento de interfaces nativas para Android.

---

# Público-Alvo

O aplicativo foi pensado principalmente para:

- Desenvolvedores iniciantes
- Desenvolvedores freelancers
- Estudantes da área de tecnologia
- Equipes pequenas de desenvolvimento
- Profissionais que necessitam organizar múltiplos projetos simultaneamente

Embora tenha sido pensado inicialmente para desenvolvedores, o aplicativo pode ser adaptado para qualquer usuário que precise gerenciar projetos pessoais ou profissionais.

---

# Justificativa da Escolha do Tema

A escolha do tema surgiu a partir da necessidade frequente de organização durante o desenvolvimento de projetos de software. Muitas vezes, tarefas, prazos e informações importantes ficam distribuídas em diferentes ferramentas.

Com isso, surgiu a ideia de desenvolver uma aplicação própria para centralizar informações dos projetos e permitir um gerenciamento mais organizado.

O tema também permitiu aplicar conteúdos importantes estudados na disciplina, como persistência local, arquitetura MVVM, gerenciamento de estado e construção de interfaces modernas.

---

# Funcionalidades Implementadas

- Cadastro de projetos
- Listagem de projetos cadastrados
- Visualização detalhada
- Definição de prioridade
- Definição de status
- Inserção de imagens pela galeria
- Definição de prazo
- Personalização de temas e cores
- Armazenamento local dos dados
- Persistência das preferências do usuário

---

# Tecnologias Utilizadas

- Kotlin
- Jetpack Compose
- Material Design 3
- Room Database
- Navigation Compose
- MVVM
- StateFlow
- DataStore
- Coil

---

# Funcionamento do Aplicativo

O aplicativo inicia exibindo a lista de projetos cadastrados.

O usuário pode:

1. Criar novos projetos
2. Adicionar informações detalhadas
3. Definir prioridades
4. Inserir imagens relacionadas
5. Definir prazos
6. Personalizar aparência e cores do sistema
7. Consultar detalhes posteriormente

Todas as informações ficam armazenadas localmente utilizando Room Database.

---

# Arquitetura Utilizada

O projeto foi estruturado utilizando o padrão MVVM (Model-View-ViewModel), buscando separar responsabilidades e facilitar manutenção e organização do código.

Estrutura principal:

```text
data/
repository/
viewmodel/
ui/
datastore/
