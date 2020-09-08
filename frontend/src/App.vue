<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="#">TASK LIST</a>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-md-12">
          <div class="button-bar">
            <b-button  @click="onShowAddTask('todo')" squared variant="primary">Nova tarefa</b-button>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="card-deck">

            <!-- todo list -->
            <div class="card">
              <div class="card-header">
                <b-button @click="onShowAddTask('todo')" size="sm" variant="primary" class="float-right">
                  <b-icon icon="plus" font-scale="2" aria-hidden="true"></b-icon>
                </b-button>
                <h5 class="card-title">Para Fazer</h5>
                <h6 class="card-subtitle text-muted">({{ todoList.length }} {{ todoList.length < 2 ? ' tarefa' : ' tarefas' }})</h6>
              </div>
              <div class="card-body">
                <add-task
                  v-if="showAddTask.todo"
                  list="TO_DO"
                  v-on:addTask="onAddTask"
                  v-on:cancelAddTask="onCancelAddTask"
                />
                <draggable class="task-list" :list="todoList" group="task" @change="changeToDoList" @end="updateToDoListOrder">
                  <task
                    v-for="(el, index) in todoList"
                    :key="el.id"
                    :id="el.id"
                    :title="el.title"
                    v-on:selectTask="onSelectTask"
                    v-on:deleteTask="onDeleteTask"
                  />
                </draggable>
              </div>
              <div class="card-footer">
                <div class="add-task">
                  <a @click="onShowAddTask('todo')" href="javascript:void(0)" class="add-button">Adicionar tarefa</a>
                </div>
              </div>
            </div>

            <!-- in progress -->
            <div class="card">
              <div class="card-header">
                <b-button @click="onShowAddTask('inProgress')" size="sm" variant="primary" class="float-right">
                  <b-icon icon="plus" font-scale="2" aria-hidden="true"></b-icon>
                </b-button>
                <h5 class="card-title">Em Andamento</h5>
                <h6 class="card-subtitle text-muted">({{ inProgressList.length }} {{ inProgressList.length < 2 ? ' tarefa' : ' tarefas' }})</h6>
              </div>
              <div class="card-body">
                <add-task
                  v-if="showAddTask.inProgress"
                  list="IN_PROGRESS"
                  v-on:addTask="onAddTask"
                  v-on:cancelAddTask="onCancelAddTask"
                />
                <draggable class="task-list" :list="inProgressList" group="task" @change="changeInProgressList" @end="updateInProgressListOrder">
                  <task
                    v-for="(el, index) in inProgressList"
                    :key="el.id"
                    :id="el.id"
                    :title="el.title"
                    v-on:selectTask="onSelectTask"
                    v-on:deleteTask="onDeleteTask"
                  />
                </draggable>
              </div>
              <div class="card-footer">
                <div class="add-task">
                  <a @click="onShowAddTask('inProgress')" href="javascript:void(0)" class="add-button">Adicionar tarefa</a>
                </div>
              </div>
            </div>

            <!-- done list -->
            <div class="card">
              <div class="card-header">
                <b-button @click="onShowAddTask('done')" size="sm" variant="primary" class="float-right">
                  <b-icon icon="plus" font-scale="2" aria-hidden="true"></b-icon>
                </b-button>
                <h5 class="card-title">Concluído</h5>
                <h6 class="card-subtitle text-muted">({{ doneList.length }} {{ doneList.length < 2 ? ' tarefa' : ' tarefas' }})</h6>
              </div>
              <div class="card-body">
                <add-task
                  v-if="showAddTask.done"
                  list="DONE"
                  v-on:addTask="onAddTask"
                  v-on:cancelAddTask="onCancelAddTask"
                />
                <draggable class="task-list" :list="doneList" group="task" @change="changeDoneList" @end="updateDoneListOrder">
                  <task
                    v-for="(el, index) in doneList"
                    :key="el.id"
                    :id="el.id"
                    :title="el.title"
                    v-on:selectTask="onSelectTask"
                    v-on:deleteTask="onDeleteTask"
                  />
                </draggable>
              </div>
              <div class="card-footer">
                <div class="add-task">
                  <a @click="onShowAddTask('done')" href="javascript:void(0)" class="add-button">Adicionar tarefa</a>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
    <task-form
      v-if="selectedTask"
      :task="selectedTask"
      v-on:saveTask="onSaveTask"
      v-on:cancelEditTask="onCancelEditTask"
    />
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import addTask from './components/AddTask'
import task from './components/Task'
import taskForm from './components/TaskForm'
export default {
  name: 'app',
  components: {
    'draggable': draggable,
    'task': task,
    'add-task': addTask,
    'task-form': taskForm
  },
  data () {
    return {
      showAddTask: {
        todo: false,
        inProgress: false,
        done: false
      },
      taskList: [],
      todoList: [],
      inProgressList: [],
      doneList: [],
      selectedTask: null
    }
  },
  mounted () {
    this.loadTaskList()
  },
  methods: {
    resetView () {
      this.showAddTask = {
        todo: false,
        inProgress: false,
        done: false
      }
    },
    loadTaskList () {
      this.$http.get('tasks/list')
        .then(result => {
          this.taskList = result.data
          this.todoList = this.taskList.filter((el) => {
            return el.status == 'TO_DO'
          })
          this.inProgressList = this.taskList.filter((el) => {
            return el.status == 'IN_PROGRESS'
          })
          this.doneList = this.taskList.filter((el) => {
            return el.status == 'DONE'
          })
        }, error => {
          console.error(error)
        })
    },
    saveTask (obj) {
      this.$http.post('tasks/save', obj)
        .then(result => {
          this.$bvToast.toast('Tarefa atualizada com sucesso', {
            variant: 'success',
            solid: true
          })
          this.resetView()
          this.loadTaskList()
        }, error => {
          console.error(error)
        })
    },
    saveAllTaskList (list) {
      this.$http.post('tasks/save-all', list)
        .then(result => {
          this.$bvToast.toast('Tarefas atualizadas com sucesso', {
            variant: 'success',
            solid: true
          })
          this.resetView()
          this.loadTaskList()
        }, error => {
          console.error(error)
        })
    },
    onShowAddTask (prop) {
      this.resetView();
      this.showAddTask[prop] = true
    },
    onSelectTask (id) {
      let selectedTaskList = this.taskList.filter((el) => {
        return el.id == id
      });
      if (selectedTaskList[0])
        this.selectedTask = Object.assign({}, selectedTaskList[0]);
    },
    onDeleteTask (id) {
      this.$http.delete(`tasks/delete/${id}`, {})
        .then(result => {
          this.$bvToast.toast('Tarefa excluída com sucesso', {
            variant: 'success',
            solid: true
          })
          this.resetView()
          this.loadTaskList()
        }, error => {
          console.error(error)
        })
    },
    onAddTask (obj) {
      let order = 0
      if (obj.list === 'TO_DO') {
        order = this.todoList.length + 1
      } else if (obj.list === 'IN_PROGRESS') {
        order = this.inProgressList.length + 1
      } else if (obj.list === 'DONE') {
        order = this.doneList.length + 1
      }

      this.saveTask({ title: obj.title, status: obj.list, order: order })
    },
    onCancelAddTask () {
      this.resetView()
    },
    onSaveTask (obj) {
      this.saveTask(obj)
      this.selectedTask = null
    },
    onCancelEditTask () {
      this.selectedTask = null
    },
    changeToDoList (evt) {
      if (evt.added) {
        let tasks = this.taskList.filter((el) => {
          return el.id == evt.added.element.id
        });
        if (tasks[0]) {
          let task = Object.assign({}, tasks[0])
          task.status = 'TO_DO'
          this.saveTask(task);
        }
      }
    },
    changeInProgressList (evt) {
      if (evt.added) {
        let tasks = this.taskList.filter((el) => {
          return el.id == evt.added.element.id
        });
        if (tasks[0]) {
          let task = Object.assign({}, tasks[0])
          task.status = 'IN_PROGRESS'
          this.saveTask(task);
        }
      }
    },
    changeDoneList (evt) {
      if (evt.added) {
        let tasks = this.taskList.filter((el) => {
          return el.id == evt.added.element.id
        });
        if (tasks[0]) {
          let task = Object.assign({}, tasks[0])
          task.status = 'DONE'
          this.saveTask(task);
        }
      }
    },
    updateToDoListOrder () {
      this.saveAllTaskList(this.todoList)
    },
    updateInProgressListOrder () {
      this.saveAllTaskList(this.inProgressList)
    },
    updateDoneListOrder () {
      this.saveAllTaskList(this.doneList)
    }
  },
}
</script>

<style lang="scss">
@import './assets/styles/variables';
@import './assets/styles/bootstrap';
@import './assets/styles/theme';
</style>
