import './style.css'

type Todo = {
  id: number
  title: string
}

const app = document.querySelector<HTMLDivElement>('#app')!

app.innerHTML = `
  <h1>Todo</h1>
  <form id="todo-form">
    <input id="todo-title" type="text" placeholder="タイトル" required />
    <button type="submit">追加</button>
  </form>
  <ul id="todo-list"></ul>
`

const list = document.querySelector<HTMLUListElement>('#todo-list')!
const form = document.querySelector<HTMLFormElement>('#todo-form')!
const titleInput = document.querySelector<HTMLInputElement>('#todo-title')!

async function fetchTodos(): Promise<Todo[]> {
  const res = await fetch('/todos')
  return res.json()
}

function renderTodos(todos: Todo[]) {
  list.replaceChildren(
    ...todos.map((todo) => {
      const li = document.createElement('li')
      li.textContent = `#${todo.id} ${todo.title}`
      return li
    }),
  )
}

async function reload() {
  renderTodos(await fetchTodos())
}

form.addEventListener('submit', async (e) => {
  e.preventDefault()
  const title = titleInput.value.trim()
  if (!title) return

  await fetch('/todos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title }),
  })

  titleInput.value = ''
  await reload()
})

reload()
