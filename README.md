# Planner SaaS - Gestão Residencial e Organizacional

Este projeto foi desenvolvido como uma solução prática para a organização e acompanhamento de projetos pessoais e profissionais. O foco principal é oferecer uma ferramenta fluida, com persistência local robusta e uma interface que se adapta ao gosto do usuário.

## 📌 Funcionalidades Principais

*   **Gestão de Projetos:** Criação, visualização detalhada e exclusão de projetos.
*   **Anexos Visuais:** Possibilidade de adicionar uma imagem de capa para cada projeto diretamente da galeria.
*   **Controle de Prazos:** Definição de datas de entrega com suporte a calendários nativos.
*   **Categorização:** Organização por prioridades (Alta, Média, Baixa) e status de progresso.
*   **Personalização de Interface:**
    *   Troca dinâmica entre modo claro e escuro.
    *   Seleção de paletas de cores (Azul, Roxo, Verde, Laranja, Vermelho).
    *   Suporte a Cores Dinâmicas (Material You) no Android 12+.
    *   **Suporte Multi-idioma:** Opção de alternar entre Português e Inglês.
    *   **Animações Fluídas:** Transições de tela e interações táteis animadas.

## 🛠️ Tecnologias Utilizadas

Para garantir um código moderno e de fácil manutenção, utilizei as seguintes tecnologias:

*   **Linguagem:** Kotlin
*   **UI:** Jetpack Compose (Material Design 3)
*   **Arquitetura:** MVVM (Model-View-ViewModel)
*   **Banco de Dados:** Room (SQLite local para os projetos)
*   **Preferências:** DataStore (para salvar as escolhas de tema e cor)
*   **Carregamento de Imagem:** Coil
*   **Navegação:** Navigation Compose

## 🏗️ Organização do Código

O projeto segue uma estrutura organizada por camadas:

*   `data/local/`: Contém a definição da entidade do banco de dados, o DAO e a configuração do Room.
*   `data/datastore/`: Responsável por gerenciar as preferências de tema do usuário.
*   `data/repository/`: Abstração que conecta a ViewModel aos dados.
*   `ui/screens/`: As telas principais (Home, Cadastro, Detalhes e Configurações).
*   `viewmodel/`: Lógica de negócio e ponte de dados para a interface.

## 🚀 Como Executar

1.  Certifique-se de ter o **Android Studio Jellyfish** ou superior.
2.  O projeto é compatível com **Android API 26 (Android 8.0)** ou superior.
3.  Após clonar o repositório, faça o **Gradle Sync**.
4.  Execute o app em um dispositivo físico ou emulador.

---
**Nota Acadêmica:** Este projeto prioriza a estabilidade e a aplicação correta dos padrões de arquitetura recomendados pelo Google para o desenvolvimento Android moderno.
