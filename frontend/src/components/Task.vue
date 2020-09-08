<template>
  <div class="task" @dblclick="onSelect">
    <b-dd variant="link" class="float-right" no-caret>
      <template slot="button-content">
        <b-icon icon="three-dots-vertical" aria-hidden="true"></b-icon>
      </template>
      <b-dd-item @click="onEdit">Editar</b-dd-item>
      <b-dd-divider />
      <b-dd-item @click="onDelete">Excluir</b-dd-item>
    </b-dd>
    <div class="task-title">{{ title }}</div>
    <div v-if="confirmDelete" class="task-delete">
      <h6>Você confirma a exclusão dessa tarefa?</h6>
      <b-button @click="doDelete" size="sm" squared variant="outline-secondary">Confirmar</b-button>
      <b-button @click="onCancel" size="sm" squared variant="outline-danger">Cancelar</b-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'task',
  props: ['title', 'id'],
  data () {
    return {
      confirmDelete: false
    }
  },
  methods: {
    onSelect () {
      this.$emit('selectTask', this.id)
    },
    onEdit () {
      this.$emit('selectTask', this.id)
    },
    onDelete () {
      this.confirmDelete = true;
    },
    doDelete () {
      this.confirmDelete = false;
      this.$emit('deleteTask', this.id)
    },
    onCancel () {
      this.confirmDelete = false;
    }
  }
}
</script>
