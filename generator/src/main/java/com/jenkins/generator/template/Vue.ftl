<template>
	<div>
		<p>
			<button @click="add()" class="btn btn-white btn-default btn-round">
				<i class="ace-icon fa fa-edit"/>
				New
			</button>
			&nbsp;
			<button @click="list(1)" class="btn btn-white btn-default btn-round">
				<i class="ace-icon fa fa-refresh"/>
				Refresh
			</button>
		</p>
		<pagination ref="pagination" :list="list" :itemCount="8"/>
		<table id="simple-table" class="table  table-bordered table-hover">
			<thead>
			<tr>
				<#list fieldList as field>
				<#if field.lowerCamelName!="createdAt" && field.lowerCamelName!="updatedAt">
				<th>${field.upperCamelName}</th>
			</#if>
			</#list>
			<th class="hidden-480">Actions</th>
			</tr>
			</thead>
			<tbody>
			<tr v-for="${entity} in ${entity}s">
				<#list fieldList as field>
				<#if field.lowerCamelName!="createdAt" && field.lowerCamelName!="updatedAt">
				<td>{{${entity}.${field.lowerCamelName}}}</td>
			</#if>
			</#list>
			<td>
				<div class="hidden-sm hidden-xs btn-group">

					<button @click="edit(${entity})" class="btn btn-xs btn-info">
						<i class="ace-icon fa fa-pencil bigger-120"/>
					</button>
					&nbsp;&nbsp;&nbsp;
					<button @click="del(${entity}.id)" class="btn btn-xs btn-danger">
						<i class="ace-icon fa fa-trash-o bigger-120"/>
					</button>

				</div>

				<div class="hidden-md hidden-lg">
					<div class="inline pos-rel">
						<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
							<i class="ace-icon fa fa-cog icon-only bigger-110"/>
						</button>

						<ul
							class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							<li>
								<a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                    <span class="blue">
                                        <i class="ace-icon fa fa-search-plus bigger-120"/>
                                    </span>
								</a>
							</li>

							<li>
								<a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                    <span class="green">
                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"/>
                                    </span>
								</a>
							</li>

							<li>
								<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                    <span class="red">
                                        <i class="ace-icon fa fa-trash-o bigger-120"/>
                                    </span>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</td>
			</tr>
			</tbody>
		</table>

		<!-- Modal -->
		<div style="margin-top: 10%" class="modal fade" id="form-modal" tabindex="-1" role="dialog"
		     aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
							aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">Save & Edit</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal">
							<#list fieldList as field>
							<div class="form-group">
								<#if field.lowerCamelName!="createdAt" && field.lowerCamelName!="updatedAt" &&field.lowerCamelName !="id">
								<label class="col-sm-2 control-label">${field.upperCamelName}</label>
								<div class="col-sm-10">
									<input v-model="${entity}.${field.lowerCamelName}" class="form-control">
								</div>
								<br>
							</#if>
							</div>
						</#list>
						</form>
					</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button @click="save" type="button" class="btn btn-primary">Save</button>
				</div>
			</div>
		</div>
	</div>

	</div>
</template>

<script>
    import pagination from "../../components/pagination";
    export default {
        name: "${module}-${Entity}",
        components: {pagination},
        data() {
            return {
            ${entity}: {},
            ${entity}s: [],
			curPage: 1,
        }
        },
        methods: {
            list(page) {
                this.axios({
                    url: "http://localhost:9000/${module}/admin/${entity}/list",
                    data: {
                        page: page,
                        pageSize: this.$refs.pagination.size
                    },
                    method: "POST"
                }).then((response) => {
                    let _response = response.data;
                    if (_response.success) {
                        this.${entity}s = _response.content.list;
                        this.$refs.pagination.render(page, _response.content.total);
                        this.curPage = this.$refs.pagination.page;
                    }
                })
            },
            add() {
                let _this = this;
                _this.${entity} = {};
                $("#form-modal").modal("show");
            },
            edit(${entity}) {
                let _this = this;
                _this.${entity} = $.extend({}, ${entity});
                $("#form-modal").modal("show");
            },
            save() {
                let _this = this;
                <#list fieldList as field>
				<#if field.lowerCamelName!="createdAt" && field.lowerCamelName!="updatedAt" &&field.lowerCamelName !="id" &&field.lowerCamelName !="sort">
				<#if !field.nullable && field.lowerCamelName !="id">
				if(! Validator.require(_this.${entity}.${field.lowerCamelName},"${field.upperCamelName}")){
					return;
				}
				</#if>
				<#if (field.length>0)>
				if(! Validator.length(_this.${entity}.${field.lowerCamelName},"${field.upperCamelName}",1,${field.length?c})){
					return;
				}
				</#if>
				</#if>
                </#list>

                // console.log(_this.${entity});
                this.axios({
                    url: "http://localhost:9000/${module}/admin/${entity}/save",
                    data: _this.${entity},
                method: "POST"
            }).then((response) => {
                    let _response = response.data;
                    if (_response.success) {
                        _this.list(_this.curPage);
                        Toast.success("Saved Successfully");
                        $("#form-modal").modal("hide");
                    }
                    else
                    {
                        Toast.warning(_response.msg)
                    }
                })
            },
            del(id) {
                let _this = this;
                console.log(_this.${entity});
                Confirm.show("You won't be able to revert this deletion", () => {
                        this.axios({
                            url: "http://localhost:9000/${module}/admin/${entity}/delete/" + id,
                            method: "DELETE"
                        }).then((response) => {
                            // console.log(response);
                            let _response = response.data;
                            if (_response.success) {
                                _this.list(_this.curPage);
                                Toast.success("Deleted Successfully!")
                            }
                        })
                    }
                )

            }
        },
        mounted() {
            let _this = this;
            _this.list(1);
            _this.${entity} = {};
        }
    }
</script>

<style scoped>

</style>