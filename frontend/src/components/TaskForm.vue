<template>
  <div class="right-form card">
    <div class="card-header">
      <span @click="onCancel" class="close">×</span>
      <h5 class="card-title">Detalhes da Tarefa</h5>
    </div>
    <div class="card-body">
      <form>
        <div class="form-group">
          <label for="title">Tarefa</label>
          <input v-model="task.title" type="text" class="form-control" id="title">
        </div>
        <div class="form-group">
          <label for="status">Status</label>
          <select v-model="task.status" class="form-control" id="status">
            <option value="TO_DO">Para Fazer</option>
            <option value="IN_PROGRESS">Em Andamento</option>
            <option value="DONE">Concluído</option>
          </select>
        </div>
        <div class="form-group">
          <label for="description">Descrição</label>
          <textarea v-model="task.description" class="form-control" id="description" rows="4"></textarea>
        </div>
        <div class="form-group">
          <label for="version">Versão</label>
          <input v-model="task.version" type="text" class="form-control" id="version">
        </div>
      </form>
    </div>
    <div class="card-footer">
      <b-button @click="onSave" squared variant="primary">Salvar</b-button>
      <b-button @click="onCancel" squared variant="outline-danger">Cancelar</b-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'taskForm',
  props: ['task'],
  methods: {
    onSave () {
      this.$emit('saveTask', this.task )
    },
    onCancel () {
      this.$emit('cancelEditTask')
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
