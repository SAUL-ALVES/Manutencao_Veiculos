# **Controle de Manutenção de Veículos**

Aplicativo Android para gerenciar veículos, registrar manutenções e acompanhar custos mensais.  
Desenvolvido em **Kotlin** com **Room** para persistência local.

---

## **Funcionalidades**

- Cadastro de um ou mais veículos.
- Registro de manutenção (troca de óleo, pneus, revisão).
- Notificação de manutenção preventiva baseada em km ou tempo.
- Consulta de preços de combustível via API.
- **Extra:** gráfico do custo de manutenção por mês.

---

## **Telas do Aplicativo**

### **1. Tela de Cadastro do Usuário**
- **Campos:**
  - Nome do usuário.
  - Orçamento mensal (*Double*).
- **Ações:**
  - Botão **“Salvar e Continuar”** → salva no banco local (**Room**) e redireciona para a tela de cadastro de veículo.

---

### **2. Tela de Cadastro do Veículo**
- **Campos:**
  - Nome do veículo (ex.: “Fiat Uno”).
  - Placa.
  - Quilometragem atual.
  - Tipo (carro, moto, caminhão) – *spinner/dropdown*.
- **Ações:**
  - Botão **“Salvar e Continuar”** → salva no **Room** vinculado ao `usuarioId` e vai para o **Dashboard**.

---

### **3. Tela de Dashboard**
- **Exibe:**
  - Nome do usuário e orçamento mensal.
  - Lista de veículos cadastrados (nome + km atual).
  - Gasto total do mês (somatório de todas as manutenções).
  - Alerta se o orçamento for ultrapassado.
- **Botões:**
  - **Histórico de Manutenções** (por veículo).
  - **Registrar Manutenção**.
- **Extras:** lembretes automáticos (ex.: “Troca de óleo em 500 km”).

---

### **4. Tela de Lista de Manutenções**
- **Exibe:**
  - Tipo de manutenção (óleo, pneus, freios, etc.).
  - Data.
  - Quilometragem.
  - Custo.
- **Funcionalidades:**
  - Mostra **total gasto no veículo**.
  - FAB (**Floating Action Button**) para **Adicionar Manutenção**.

---

## **Ordem de Desenvolvimento**

### **1. Configuração inicial**
- Criar projeto Android Studio com **Kotlin**.
- Definir **XML** ou **Jetpack Compose** (recomendado: Compose).
- Configurar **Navigation Component** para navegação entre telas.
- Criar pacotes:
  - `ui` → telas e layouts
  - `data` → entidades e DAOs do Room
  - `viewmodel` → lógica de apresentação

---

### **2. Tela de Cadastro do Usuário**
- Layout com campos de nome e orçamento.
- Salvar no **Room**.
- Redirecionar para **Cadastro de Veículo**.

---

### **3. Tela de Cadastro do Veículo**
- Layout com campos básicos.
- Salvar no **Room** vinculado ao `usuarioId`.
- Redirecionar para o **Dashboard**.

---

### **4. Tela de Dashboard**
- Criar layout inicial com dados mockados.
- Mostrar lista de veículos e resumo do orçamento.
- Criar botões para histórico e registro de manutenção.

---

### **5. Tela de Lista de Manutenções**
- Criar RecyclerView (XML) ou LazyColumn (Compose) com dados mockados.
- Estrutura do item: tipo, data, km, custo.
- Adicionar FAB para registrar manutenção.

---

### **6. Integração com Banco de Dados**
- Criar entidades:
  - `Usuario`
  - `Veiculo` (com chave estrangeira para `Usuario`)
  - `Manutencao` (com chave estrangeira para `Veiculo`)
- Implementar DAO e repositório no **Room**.
- Substituir dados mockados pelos reais.

---

## **Tecnologias utilizadas**
- **Kotlin**
- **Room Database**
- **Android Jetpack (Navigation, ViewModel, LiveData)**
- **(Opcional) Jetpack Compose para UI**
- **API externa** para consulta de preços de combustível

---

## **Próximos passos**
- Implementar notificações de manutenção preventiva.
- Adicionar gráfico de custo mensal.
- Publicar no GitHub com documentação detalhada.

---
