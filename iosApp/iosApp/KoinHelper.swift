//
//  KoinHelper.swift
//  iosApp
//
//  Created by Efekan YILMAZ on 5.04.2025.
//  Copyright © 2025 Codekan Labs. All rights reserved.
//



import SwiftUI
import shared

class KoinHelper {
    static let shared = KoinHelper()
    // Koin initializer implemented in common.
    private let koinInitializer: KoinInitializer!
    
    private init() {
        // Dependency injection initial steps handled.
        let driverFactory = DatabaseDriverFactory()
        self.koinInitializer = KoinInitializer(driverFactory: driverFactory)
        // Check commonMain package for doInitKoin function.
        self.koinInitializer.doInitKoin()
    }
    
    func getNotesViewModel() -> NotesViewModel {
        // Viewmodel injection for iosApp
        return koinInitializer.getNotesViewModel()
    }
}


