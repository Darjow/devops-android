package com.hogent.android.network.dtos.responses

import com.hogent.android.domain.VirtualMachineModus

class VMIndex(val id: Int, val name: String, val mode: VirtualMachineModus, var projectId: Int?)
