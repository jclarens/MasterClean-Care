<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUserWorkTimesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('user_work_times', function (Blueprint $table) {
            $table->integer('userId');
            $table->integer('workTimeId');
            $table->decimal('cost');
            $table->timestamps();

            $table->foreign('userId')->references('id')->on('users');
            $table->foreign('workTimeId')->references('id')->on('work_times');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('user_work_times');
    }
}
