# Planner SaaS

## Informações do Aluno

Nome: Raynara Kácia Magalhães Fonteles

Data de entrega: 03/06/2026  

---

# Sobre o Projeto

O Planner SaaS é um aplicativo Android desenvolvido com o objetivo de ajudar na organização e gerenciamento de projetos. A ideia principal foi criar uma ferramenta voltada principalmente para desenvolvedores, já que durante o desenvolvimento de software normalmente existem muitas tarefas, prazos e informações espalhadas.

O aplicativo funciona como uma agenda de gerenciamento, permitindo cadastrar projetos, organizar informações importantes e acompanhar o andamento de cada projeto.

---

# Objetivo do Projeto

O objetivo do projeto foi desenvolver um aplicativo que ajudasse desenvolvedores a organizar melhor seus projetos e atividades, trazendo mais organização e eficiência durante o desenvolvimento.

Além disso, o projeto também teve como objetivo colocar em prática conteúdos estudados durante a disciplina, principalmente desenvolvimento Android, persistência de dados, arquitetura MVVM e construção de interfaces modernas.

---

# Público-Alvo

O aplicativo foi pensado principalmente para:

- Desenvolvedores
- Estudantes da área de tecnologia
- Freelancers
- Pessoas que trabalham com vários projetos ao mesmo tempo

Mesmo sendo pensado inicialmente para desenvolvedores, ele também pode ser utilizado para organização pessoal.

---

# Funcionalidades Implementadas

Durante o desenvolvimento foram implementadas as seguintes funcionalidades:

- Cadastro de projetos
- Listagem dos projetos cadastrados
- Tela de detalhes do projeto
- Definição de prioridade
- Definição de status
- Adição de imagens usando a galeria do celular
- Definição de prazo
- Temas personalizados
- Alteração de cores do aplicativo
- Armazenamento local dos dados

---

# Tecnologia Utilizada

As principais tecnologias utilizadas foram:

- Kotlin
- Jetpack Compose
- Room Database
- Navigation Compose
- MVVM
- StateFlow
- DataStore
- Material Design 3
- Coil


---

# Como o Aplicativo Funciona

Ao abrir o aplicativo, o usuário consegue visualizar os projetos cadastrados.

Para adicionar um novo projeto, basta preencher as informações necessárias, como:

- título
- descrição
- prioridade
- status
- prazo
- imagem

Todas as informações ficam armazenadas localmente no dispositivo.

Também foi criada uma área de configurações onde o usuário pode personalizar o tema e as cores do aplicativo.

---

# Organização do Projeto

O projeto foi organizado utilizando arquitetura MVVM para separar melhor as responsabilidades.

Estrutura principal:

```text
data/
repository/
viewmodel/
ui/
datastore/
```

---

# Dificuldades Encontradas

Durante o desenvolvimento surgiram algumas dificuldades, principalmente relacionadas à configuração do ambiente, integração de funcionalidades e testes no emulador Android.

Parte dessas dificuldades foi resolvida utilizando dispositivo físico para validação do aplicativo.

---

# Como Executar

1. Clonar o repositório.
2. Abrir o projeto no Android Studio.
3. Sincronizar as dependências.
4. Executar no celular ou emulador Android.

---

# Considerações Finais
O desenvolvimento desse projeto permitiu aplicar conceitos estudados durante a disciplina e entender melhor como funciona o desenvolvimento Android utilizando ferramentas modernas.

Além disso, foi possível trabalhar organização de código, persistência de dados e construção de interfaces utilizando Compose.
