<template>
  <div class="add-task">
      <form>
        <input type="text" v-model="title" class="form-control" placeholder="Escreva o nome da tarefa"/>
      </form>
      <b-button :disabled="title.length < 3" @click="onAdd" size="sm" squared variant="outline-secondary">Adicionar tarefa</b-button>
      <b-button @click="onCancel" size="sm" squared variant="outline-danger">Cancelar</b-button>
  </div>
</template>

<script>
export default {
  name: 'addTask',
  props: ['list'],
  data () {
    return {
      title: ''
    }
  },
  methods: {
    onAdd () {
      this.$emit('addTask', { 'list': this.list, 'title': this.title })
      this.title = '';
    },
    onCancel () {
      this.$emit('cancelAddTask')
      this.title = '';
    }
  }
}
</script>

<style lang="scss">
form {
  margin-bottom: 10px;
}
button.disabled {
  cursor: not-allowed;
}
</style>
