package com.example.scbbluetooth.data.repositorypppppppppppppppppppppppppp

import com.example.scbbluetooth.data.database.AppDatabase
import com.example.scbbluetooth.data.network.Api
import com.example.scbbluetooth.data.repository.Repository

class RepositoryImpl constructor(
    private val api: Api,
    private val db: AppDatabase
): Repository {
    override fun login(login: String, password: String) {

    }
}
